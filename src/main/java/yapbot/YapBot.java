package yapbot;

import yapbot.storage.Storage;
import yapbot.ui.UI;

public class YapBot
{
    public static void main(String[] args)
    {
        run(args);
    }

    private static void run(String[] args)
    {
        UI.greeting();
        Storage.loadTasks();
        Storage.readTasks(args);
        Storage.writeTasks();
    }
}
