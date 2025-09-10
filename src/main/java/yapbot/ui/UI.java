package yapbot.ui;

import yapbot.taskmanager.Task;
import yapbot.taskmanager.TaskList;

public class UI {
    private static final String SYSTEM_LINE_BREAK = "\n____________________________________________________________";
    private static final String SYSTEM_MESSAGE_GREETING =
            """
                                  Hello! I'm YapBot
                                What can I do for you?
                        Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String SYSTEM_MESSAGE_FAREWELL =
            """
                         Bye. Hope to see you again soon!
            ____________________________________________________________
            """;
    private static final String SYSTEM_MESSAGE_CREATING_FILE =
            """
                        Creating a brand new file for you...
            ____________________________________________________________
            """;
    private static final String SYSTEM_MESSAGE_CREATED_FILE =
            """
                            Your file has been created!
            ____________________________________________________________
            """;
    private static final String SYSTEM_MESSAGE_LOADING_FILE =
            """
                        Loading your previous tasks for you...
            ____________________________________________________________
            """;
    private static final String SYSTEM_MESSAGE_LOADED_FILE =
            """
                            Your tasks has been loaded!
            ____________________________________________________________
            """;
    private static final String SYSTEM_MESSAGE_HELP = """
            Here is a list of valid commands ('-' is reserved for flags)
                [T][_]~ adds a ToDo task to your list
                [T][X]~ adds a marked ToDo task to your list
                [D][_]~ adds a Deadline task to your list
                [D][X]~ adds a marked Deadline task to your list
                    flags:
                        -by DD/MM/YYYY      the specified deadline
                [E][_]~ adds an Event task to your list
                [E][X]~ adds a marked Event task to your list
                    flags:
                        -from~              start date/time
                        -to~                end date/time
                [mark]x marks the 'x-th' task as done
                [unmark]x marks the 'x-th' task as not done
                [delete]x deletes the 'x-th' task from your list
                [list]~ shows a list of available tasks
                [bye]~ exits the chatbot
            ____________________________________________________________
            """;

    private static final String SYSTEM_ERROR_INVALID_COMMAND =
            """
                    OOPS!!! I'm sorry, the command does not exists :-(
                    Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String SYSTEM_ERROR_INVALID_DATE_FORMAT =
            """
                    OOPS!!! I'm sorry, the date provided is invalid :-(
                    Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String SYSTEM_ERROR_INVALID_FLAG =
            """
                    OOPS!!! I'm sorry, the flag does not exists :-(
                    Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String SYSTEM_ERROR_INVALID_FILE =
            """
                    OOPS!!! I'm sorry, the file does not exists :-(
                    Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String SYSTEM_ERROR_TASK_NOT_FOUND =
            """
               OOPS!!! I'm sorry, the task could not be located :-(
            ____________________________________________________________
            """;

    public static String lineBreak() {
        System.out.println(SYSTEM_LINE_BREAK);
        return SYSTEM_LINE_BREAK;
    }

    public static String greeting() {
        System.out.println(SYSTEM_MESSAGE_GREETING);
        return SYSTEM_MESSAGE_GREETING;
    }

    public static String farewell() {
        System.out.println(SYSTEM_MESSAGE_FAREWELL);
        return SYSTEM_MESSAGE_FAREWELL;
    }

    public static String creatingFile() {
        System.out.println(SYSTEM_MESSAGE_CREATING_FILE);
        return SYSTEM_MESSAGE_CREATING_FILE;
    }

    public static String createdFile() {
        System.out.println(SYSTEM_MESSAGE_CREATED_FILE);
        return SYSTEM_MESSAGE_CREATED_FILE;
    }

    public static String loadingFile() {
        System.out.println(SYSTEM_MESSAGE_LOADING_FILE);
        return SYSTEM_MESSAGE_LOADING_FILE;
    }

    public static String loadedFile() {
        System.out.println(SYSTEM_MESSAGE_LOADED_FILE);
        return SYSTEM_MESSAGE_LOADED_FILE;
    }

    public static String help() {
        System.out.println(SYSTEM_MESSAGE_HELP);
        return SYSTEM_MESSAGE_HELP;
    }

    public static String markedTask(Task task) {
        String response = "Nice! I've marked this task as done:\n" + echo(task);
        System.out.println(response);
        return response;
    }

    public static String unmarkedTask(Task task) {
        String response = "OK, I've marked this task as not done yet:\n" + echo(task);
        System.out.println(response);
        return response;
    }

    public static String deletedTask(Task task) {
        String response = "Noted. I've removed this task:\n" + echo(task);
        System.out.println(response);
        return response;
    }

    public static String echo(Task task) {
        String response = task + "\n" +
                "Now you have " + TaskList.numOfTasks() + " tasks in the list\n" +
                UI.lineBreak();
        return response;
    }

    public static String invalidCommand() {
        System.out.println(SYSTEM_ERROR_INVALID_COMMAND);
        return SYSTEM_ERROR_INVALID_COMMAND;
    }

    public static String invalidDateFormat() {
        System.out.println(SYSTEM_ERROR_INVALID_DATE_FORMAT);
        return SYSTEM_ERROR_INVALID_DATE_FORMAT;
    }

    public static String invalidFlag() {
        System.out.println(SYSTEM_ERROR_INVALID_FLAG);
        return SYSTEM_ERROR_INVALID_FLAG;
    }

    public static String invalidFile() {
        System.out.println(SYSTEM_ERROR_INVALID_FILE);
        return SYSTEM_ERROR_INVALID_FILE;
    }

    public static String taskNotFound() {
        System.out.println(SYSTEM_ERROR_TASK_NOT_FOUND);
        return SYSTEM_ERROR_TASK_NOT_FOUND;
    }
}
