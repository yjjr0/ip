package yapbot;

import yapbot.storage.Storage;
import yapbot.ui.UI;

public class YapBot  {
    public static void main(String[] args) {
        run(args);
    }

    /**
     * Runs the chatbot by calling loadTasks -> readTasks -> writeTasks sequentially
     *
     * @param args an array of commands to be processed by the chatbot
     */
    private static void run(String[] args) {
        UI.greeting();
        Storage.loadTasks();
        Storage.readTasks(args);
        Storage.writeTasks();
    }
}
