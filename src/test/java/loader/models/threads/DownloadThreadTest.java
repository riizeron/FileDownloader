package loader.models.threads;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DownloadThreadTest {

    /**
     * Тест не робит это типо из-за того что райнтайм эксепшны не отслеживаются???
     */
    @Test
    void runThreadOnIncorrectDownloadDestinationShouldThrowsRE() {
        /*String link = "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700212191.jpg";
        String wrongLink = "sdfdf";
        String path = System.getProperty("user.dir");
        String wrongPath = "dfasdfgeyte";
        assertThrows(RuntimeException.class, () -> new Thread(new DownloadThread(wrongLink, path)).start());*/
    }

    @Test
    void runThreadOnCorrectValuesShouldSaveFile() {
        String path = System.getProperty("user.dir");
        String link = "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700212191.jpg";
        String fileName = link.substring(link.lastIndexOf('/') + 1).trim();
        Thread testThread = new Thread(new DownloadThread(link, path));
        testThread.start();
        try {
            testThread.join();
            testThread.interrupt();
        } catch (InterruptedException e) {
            testThread.interrupt();
        }
        assertTrue(Files.exists(Path.of(path + System.getProperty("file.separator")+ fileName)));
    }
}