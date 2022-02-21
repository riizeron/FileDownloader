package loader.models;

import loader.models.interfaces.Loader;
import loader.models.threads.DownloadThread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileDownloader implements Loader {
    private Boolean isEnd = false;
    private String path = System.getProperty("user.dir");
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public final void help() {
        System.out.println("""
                CLI File Downloader commands:
                /help - показ всех комманд и справки
                /load <URL> - загрузка файла по урлу
                /load <URL1> <URL2> ... - загрузка файлов по урлам
                /dest <PATH> - указать путь куда скачивать файлы
                /exit - выход
                Current download destination is""" + " " + getPath());
    }

    public void load(String urls) {
        List<String> strUrls = Arrays.stream(urls.split("\s+")).toList();
        for (var strUrl : strUrls) {
            loadElem(strUrl);
        }
    }

    private void loadElem(String link) {
        pool.execute(new DownloadThread(link, path));
    }

    public void dest(String path) throws IOException {
        if (Files.isDirectory(Path.of(path))) {
            this.path = path;
            System.out.println("You currently change the direct path");
        } else {
            throw new IOException("Wrong destination directory");
            //System.out.println("Wrong destination directory");
        }
    }

    public void exit() {
        isEnd = true;
        pool.shutdown();
        try {
            if (!pool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    public String getPath() {
        return path;
    }

    public Boolean isEnd() {
        return isEnd;
    }

    public Boolean isPoolShutdown() {
        return pool.isShutdown();
    }
}
