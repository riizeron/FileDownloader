package loader;

import loader.models.CommandDispatcher;
import loader.models.controllers.Controller;
import loader.models.FileDownloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("""
                Hi there!
                This is my concurrency file downloader!
                Type /help to get commands""");

        String command;
        FileDownloader fd = new FileDownloader();
        CommandDispatcher cd = new CommandDispatcher(new Controller(fd));
        while(!fd.isEnd()) {
            System.out.print(fd.getPath() + ">");
            command = scanner.nextLine();
            try {
                cd.executeCommand(command);
            } catch (NoSuchMethodException ex) {
                System.out.println("There is no such annotated method");
            } catch (IOException ex) {
                System.out.println("asdasdasds");
            } catch (IllegalAccessException | InvocationTargetException ex) {
                System.out.println(ex.getMessage());
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                fd.flush();
            }
        }
    }
}
