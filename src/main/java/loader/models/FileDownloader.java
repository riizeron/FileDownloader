package loader.models;

import loader.models.threads.DownloadThread;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileDownloader {
    private Boolean isEnd = false;
    private String path = System.getProperty("user.dir");

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
        URLConnection connection;
        try {
            for (var strUrl : strUrls) {
                connection = new URL(strUrl).openConnection();
                load(connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(URLConnection connection) throws IOException {
        int threadAmount = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(threadAmount);
        int wholeLen = connection.getContentLength()/threadAmount;
        int eachLen = wholeLen/threadAmount;
        String fileName = "ну типа";
        for (int i = 0; i < threadAmount; i++) {
            long start = (long) eachLen * i;
            long end = (long) eachLen * (i + 1);
            if (i == threadAmount - 1) end = Math.max(end, wholeLen);

            connection.setRequestProperty("Range", "bytes=" + String.valueOf(start) + "-" + String.valueOf(end));
            connection.connect();

            String suff = ".";

            RandomAccessFile raf = new RandomAccessFile(fileName + suff, "rw");
            raf.seek(start);    // Поток файла в соответствующее местоположение

            pool.submit(new DownloadThread(connection, raf));
        }
    }

    public void dest(String path) {
        this.path = path;
    }

    public void flush() {
        isEnd = true;
    }

    public String getPath() {
        return path;
    }

    public Boolean isEnd() {
        return isEnd;
    }
}
