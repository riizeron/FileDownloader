package loader.models;

import loader.models.annotations.Command;

import java.util.List;

public record Controller(FileDownloader fd) {

    @Command("/help")
    void help() {
        fd.help();
    }

    @Command("/load")
    void load(List<String> urls) {
        fd.loadAll(urls);
    }

    @Command("/exit")
    void exit() {
        fd.flush();
    }

    @Command("/dest")
    void dest(String path) {
        fd.dest(path);
    }

}
