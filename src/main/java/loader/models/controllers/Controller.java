package loader.models.controllers;

import loader.models.annotations.Command;
import loader.models.interfaces.Loader;

import java.io.IOException;

public record Controller(Loader loader) {

    @Command("/help")
    void help() {
        loader.help();
    }

    @Command("/load")
    void load(String strUrl) {
        //List<String> urls = List.of(strUrl.split("\\s+"));
        loader.load(strUrl);
    }

    @Command("/exit")
    void exit() {
        loader.exit();
    }

    @Command("/dest")
    void dest(String path) throws IOException {
        loader.dest(path);
    }
}
