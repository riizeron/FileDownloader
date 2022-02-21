package loader.models;

import loader.models.annotations.Command;
import loader.models.interfaces.Loader;
import org.reflections.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandDispatcher {

    private final Loader loader;
    private final Map<String, Method> methodHashMap = new HashMap<>();
    private Method defaultMethod;

    public CommandDispatcher(Loader loader) {
        Objects.requireNonNull(loader);
        this.loader = loader;

        for (Method method : ReflectionUtils.getAllMethods(loader.getClass())) {
            Command command = method.getAnnotation(Command.class);
            if (command != null) {
                String commandId = command.value();
                if (commandId.isEmpty()) {
                    defaultMethod = method;
                } else {
                    methodHashMap.put(commandId, method);
                }
            }
        }
    }

    public void executeCommand(String commandLine) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        String command;
        String params;

        if (commandLine.indexOf(' ') == -1) {
            command = commandLine;
            params = null;
        } else {
            command = commandLine.substring(0, commandLine.indexOf(' '));
            params = commandLine.substring(commandLine.indexOf(' ') + 1);
        }
        Method method = methodHashMap.getOrDefault(command, defaultMethod);
        if (method == null) {
            throw new NoSuchMethodException("There is no such annotated method");
        }
        if (params == null) {
            method.invoke(loader);
        } else {
            method.invoke(loader, params);
        }
    }
}
