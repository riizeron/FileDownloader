package loader.models.threads;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public record DownloadThread(String link, String path) implements Runnable {

    @Override
    public void run() {
        try {
            String fileName = link.substring(link.lastIndexOf('/') + 1).trim();
            RandomAccessFile raf = new RandomAccessFile(path + System.getProperty("file.separator")
                    + fileName, "rw");

            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            double fileSize = connection.getContentLength();
            InputStream is = connection.getInputStream();

            double downloaded = 0.00;
            double percent;
            byte[] buffer = new byte[2 ^ 10];
            int len;
            while ((len = is.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
                downloaded += len;
                percent = (downloaded * 100) / fileSize;
                System.out.println("Downloaded " + String.format("%.4f", percent) + "% of file");
            }
            raf.close();
            System.out.println("Download complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
