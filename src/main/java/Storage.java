import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private static final String FILENAME = "tasks.txt";

    public static void loadTasks() {
        try {
            File file = new File(FILENAME);
            if (file.createNewFile()) {
                UI.creatingFile();
                UI.createdFile();
            } else {
                UI.loadingFile();
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    Parser.storeTask(scanner.nextLine());
                }
                UI.loadedFile();
                Parser.list();
            }
        } catch (IOException exception) {
            UI.taskNotFound();
        } catch (SecurityException exception) {
            UI.taskNotFound();
        }
    }

    public static void readTasks(String[] args) {
        for (String task : args) {
            System.out.println(task);

            if (task.startsWith("[h]") || task.startsWith("[H]")) {
                UI.help();
            } else if (task.startsWith("[mark]")) {
                Parser.mark(task);
            } else if (task.startsWith("[unmark]")) {
                Parser.unmark(task);
            } else if (task.startsWith("[delete]")) {
                Parser.delete(task);
            } else if (task.startsWith("[list]")) {
                Parser.list();
            } else if (task.startsWith("[bye]")) {
                UI.farewell();
            } else {
                Parser.storeTask(task);
            }
        }
    }

    public static void writeTasks() {
        try {
            FileWriter fileWriter = new FileWriter(FILENAME);
            String tasks = TaskList.getTasksAsTXT();
            fileWriter.write(tasks);
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println("An error has occurred");
        }
    }
}
