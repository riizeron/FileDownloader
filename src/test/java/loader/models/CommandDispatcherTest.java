package loader.models;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommandDispatcherTest {

    @Test
    void executeCommandWithIncorrectAnnotationShouldThrowsNoSuchMethodException() {
        CommandDispatcher cd = new CommandDispatcher(new Controller(new FileDownloader()));
        assertThrows(NoSuchMethodException.class, () -> cd.executeCommand("dfhasfgf"));
    }
}