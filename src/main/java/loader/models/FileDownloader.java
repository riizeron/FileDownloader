package loader.models;

import loader.models.threads.DownloadThread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileDownloader {
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
                /exit - выход""");
    }

    public void loadAll(List<String> strUrls) {
        for (var strUrl : strUrls) {
            load(strUrl);
        }
    }

    private void load(String link) {
        pool.execute(new DownloadThread(link, path));
    }

    public void dest(String path) {
        this.path = path;
    }

    public void flush() {
        isEnd = true;
        pool.shutdown();
    }

    public String getPath() {
        return path;
    }

    public Boolean isEnd() {
        return isEnd;
    }
}
