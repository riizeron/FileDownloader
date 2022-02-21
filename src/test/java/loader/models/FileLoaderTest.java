package loader.models;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class FileLoaderTest extends TestCase {

    static FileDownloader fd;
    @BeforeAll
    public static void setUpFD() {
        fd = new FileDownloader();
    }
    @AfterAll
    public static void endOff() {
        fd.exit();
    }
    /**
     * Это че еще за высер джупитер винтаж а??
     * Откуда он вообще взялся и че этот тест не прохидит??
     */
    @Test
    public void loadAllOnCorrectPathShouldSaveFiles() {
        String link1 = "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77700212191.jpg";
        String link2 = "https://i.ytimg.com/vi/22mb8TAKTIk/maxresdefault.jpg";
        //List<String> urls = List.of(new String[]{link1, link2});
        fd.load(String.join(" ", link1, link2));
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
        try {
        fd.dest("incorectPath");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path2 = fd.getPath();
        assertEquals(path1, path2);
    }

    @Test
    public void destOnIncorrectPathShouldThrowIOException() {
        assertThrows(IOException.class, () -> fd.dest("incorrectPath"));
    }

    @Test
    public void flushShouldShutThePool() {
        fd.exit();
        assertTrue(fd.isPoolShutdown());
    }
}