package loader.models;

import loader.models.annotations.Command;

import java.util.List;

public class CommandDispatcher {
    final Controller controller;
    List<String> commandList;

    public CommandDispatcher(Controller controller) {
        this.controller = controller;
    }

    public void executeCommand(String command) {
        commandList = List.of(command.split("\\s+"));
        switch (commandList.get(0)) {
            case "/help" -> controller.help();
            case "/exit" -> controller.exit();
            case "/load" -> controller.load(commandList.subList(1, commandList.size()));
            case "/dest" -> controller.dest(String.join(" ", commandList.subList(1, commandList.size())));
        }
    }
}
