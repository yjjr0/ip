public class UI {
    private static final String GREETING =
            """
            ____________________________________________________________
                                  Hello! I'm YapBot
                                What can I do for you?
                        Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String FAREWELL =
            """
                         Bye. Hope to see you again soon!
            ____________________________________________________________
            """;
    private static final String CREATINGFILE =
            """
                        Creating a brand new file for you...
            ____________________________________________________________
            """;
    private static final String CREATEDFILE =
            """
                            Your file has been created!
            ____________________________________________________________
            """;
    private static final String LOADINGFILE =
            """
                        Loading your previous tasks for you...
            ____________________________________________________________
            """;
    private static final String LOADEDFILE =
            """
                            Your tasks has been loaded!
            ____________________________________________________________
            """;
    private static final String HELP = """
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
    private static final String INVALIDCOMMAND =
            """
                    OOPS!!! I'm sorry, the command does not exists :-(
                    Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String INVALIDFLAG =
            """
                    OOPS!!! I'm sorry, the flag does not exists :-(
                    Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String TASKNOTFOUND =
            """
               OOPS!!! I'm sorry, the task could not be located :-(
            ____________________________________________________________
            """;

    public static void greeting() {
        System.out.println(GREETING);
    }

    public static void farewell() {
        System.out.println(FAREWELL);
    }

    public static void creatingFile() {
        System.out.println(CREATINGFILE);
    }

    public static void createdFile() {
        System.out.println(CREATEDFILE);
    }

    public static void loadingFile() {
        System.out.println(LOADINGFILE);
    }

    public static void loadedFile() {
        System.out.println(LOADEDFILE);
    }

    public static void help() {
        System.out.println(HELP);
    }

    public static void invalidCommand() {
        System.out.println(INVALIDCOMMAND);
    }

    public static void invalidFlag() {
        System.out.println(INVALIDFLAG);
    }

    public static void taskNotFound() {
        System.out.println(TASKNOTFOUND);
    }
}
