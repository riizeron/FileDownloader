package loader;

import loader.models.CommandDispatcher;
import loader.models.Controller;
import loader.models.FileDownloader;

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
            System.out.println("Current download destination is " + fd.getPath());
            System.out.print(fd.getPath() + ">");
            command = scanner.nextLine();
            try {
                cd.executeCommand(command);
            } catch (NoSuchMethodException ex) {
                System.out.println("There is no such annotated method");
            } catch (IllegalAccessException | InvocationTargetException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                fd.flush();
            }
        }
    }
}
