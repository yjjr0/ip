package yapbot.storage;

import yapbot.parser.Parser;
import yapbot.taskmanager.TaskList;
import yapbot.ui.UI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    private static final String FILENAME = "tasks.txt";

    /**
     * Loads data from remote storage (tasks.txt file) to store into local storage (TASKS arraylist)
     */
    public static void loadTasks() {
        try {
            File file = new File(FILENAME);
            if (file.createNewFile()) {
                UI.creatingFile();
                UI.createdFile();
            } else {
                UI.loadingFile();
                Parser.loadFile(file);
                UI.loadedFile();
                Parser.list();
            }
        } catch (IOException exception) {
            UI.invalidFile();
        } catch (SecurityException exception) {
            UI.invalidFile();
        }
    }

    /**
     * Reads data from the CLI (String[] args) to store into local storage (TASKS arraylist)
     */
    public static void readTasks(String[] args) {
        for (String task : args) {
            System.out.println(task);
            Parser.setTask(task);
        }
    }

    /**
     * Writes data from the local storage (TASKS arraylist) to store into remote storage (tasks.txt file)
     */
    public static void writeTasks() {
        try {
            FileWriter fileWriter = new FileWriter(FILENAME);
            String tasks = TaskList.getTasksAsTxt();
            fileWriter.write(tasks);
            fileWriter.close();
        } catch (IOException exception) {
            UI.invalidFile();
        }
    }
}
