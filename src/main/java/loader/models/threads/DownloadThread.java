package loader.models.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicLong;

public class DownloadThread implements Runnable {
    private final URLConnection connection;
    private final RandomAccessFile raf;
    public static AtomicLong process;

    public DownloadThread(URLConnection connection, RandomAccessFile raf) {
        this.connection = connection;
        this.raf = raf;
    }

    @Override
    public void run() {
        try {
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[2 ^ 20];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
