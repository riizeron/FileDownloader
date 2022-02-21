package loader.models.interfaces;

import loader.models.annotations.Command;

import java.io.IOException;

public interface Loader {
    @Command("/help")
    void help();

    @Command("/load")
    void load(String strUrl);

    @Command("/exit")
    void exit();

    @Command("/dest")
    void dest(String path) throws IOException;
}
