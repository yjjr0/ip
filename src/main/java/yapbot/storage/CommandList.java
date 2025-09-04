package yapbot.storage;

import yapbot.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class CommandList {
    private static final List<String> COMMANDS = new ArrayList<>();
    private static int CURRENT = 0;

    /**
     * Adds the current command into the list
     */
    public static void addCommand(String command) {
        COMMANDS.add(command);
        CURRENT++;
    }

    /**
     * Gets the previous command in the list
     */
    public static String getPreviousCommand() {
        try {
            if (CURRENT > 0) {
                CURRENT--;
            }
            return COMMANDS.get(CURRENT);
        } catch (IndexOutOfBoundsException exception) {
            return UI.invalidCommand();
        }
    }

    /**
     * Gets the next command in the list
     */
    public static String getNextCommand() {
        try {
            if (CURRENT < COMMANDS.size() - 1) {
                CURRENT++;
            }
            return COMMANDS.get(CURRENT);
        } catch (IndexOutOfBoundsException exception) {
            return UI.invalidCommand();
        }
    }
}
