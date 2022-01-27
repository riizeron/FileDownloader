package loader.models;

import loader.models.annotations.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public record CommandDispatcher(Controller controller) {
    // private List<String> commandsss;

    public void executeCommand(String commandLine) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String command = commandLine.substring(0, commandLine.indexOf(' ') - 1);
        Method method = getAnnotatedMethod(command);
        if (method == null) {
            throw new NoSuchMethodException();
        }
        String params = commandLine.substring(commandLine.indexOf(' ') + 1);
        if (params.length() == 0) {
            params = null;
        }
        method.invoke(controller, params);

        // commandsss = List.of(commandLine.split("\\s+"));
        /*switch (commandsss.get(0)) {
            case "/help" -> controller.help();
            case "/exit" -> controller.exit();
            case "/load" -> controller.load(commandsss.subList(1, commandsss.size()));
            case "/dest" -> controller.dest(String.join(" ", commandsss.subList(1, commandsss.size())));
        }*/
    }

    private Method getAnnotatedMethod(String command) {
        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                if (Objects.equals(method.getAnnotation(Command.class).value(), command)) {
                    method.setAccessible(true);
                    return method;
                }
            }
        }
        return null;
    }
}
