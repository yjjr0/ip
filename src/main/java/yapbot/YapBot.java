package yapbot;

import yapbot.parser.Parser;
import yapbot.storage.Storage;
import yapbot.ui.UI;

public class YapBot {
    public static void main(String[] args) {
        run(args);
    }

    /**
     * Runs the chatbot by using the CLI
     *
     * @param args an array of commands to be processed by the chatbot
     */
    private static void run(String[] args) {
        UI.greeting();
        Storage.loadTasks();
        Storage.readTasks(args);
        Storage.writeTasks();
    }

    /**
     * Runs the chatbot by using the GUI
     *
     * @param task the input command from the user
     */
    public static String getResponse(String task) {
        return Parser.setTask(task);
    }
}