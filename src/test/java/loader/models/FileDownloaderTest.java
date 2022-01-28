package loader.models;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileDownloaderTest extends TestCase {

    static FileDownloader fd;
    @BeforeAll
    public static void setUpFD() {
        fd = new FileDownloader();
    }
    @AfterAll
    public static void endOff() {
        fd.flush();
    }
    /**
     * Это че еще за высер джупитер винтаж а??
     * Откуда он вообще взялся и че этот тест не прохидит??
     */
    @Test
    public void loadAllOnCorrectPathShouldSaveFiles() {
        String link1 = "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700212191.jpg";
        String link2 = "https://i.ytimg.com/vi/22mb8TAKTIk/maxresdefault.jpg";
        List<String> urls = List.of(new String[]{link1, link2});
        fd.loadAll(urls);
        String fileName1 = link1.substring(link1.lastIndexOf('/')).trim();
        String fileName2 = link2.substring(link2.lastIndexOf('/')).trim();
        assertTrue(Files.exists(Path.of(fd.getPath() + System.getProperty("file.separator")+ fileName1))
                && Files.exists(Path.of(fd.getPath() + System.getProperty("file.separator")+ fileName2)));
    }

    /**
     * Да кто блин такой этот винтаж????
     * Эта че такое java.lang.Exception: No tests found matching Method
     * destOnIncorrectPathShouldntChangePath(loader.models.FileDownloaderTest)
     * from org.junit.vintage.engine.descriptor.RunnerRequest@2b552920 ???
     */
    @Test
    public void destOnIncorrectPathShouldntChangePath() {
        String path1 = fd.getPath();
        fd.dest("incorectPath");
        String path2 = fd.getPath();
        assertEquals(path1, path2);
    }

    @Test
    public void flushShouldShutThePool() {
        fd.flush();
        assertTrue(fd.isPoolShutdown());
    }
}