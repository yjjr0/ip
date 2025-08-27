import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class YapBot {
    private static final String NAME = "YapBot";
    private static final String FILENAME = "tasks.txt";
    private static final List<Task> TASKS = new ArrayList<>();
    private static final String GREETING =
            "____________________________________________________________\n" +
            "                      Hello! I'm " + NAME + "\n" +
            """
                                What can I do for you?
                        Enter [h]/[H] for a list of valid commands
            ____________________________________________________________
            """;
    private static final String FAREWELL =
            """
                         Bye. Hope to see you again soon!
            ____________________________________________________________
            """;
    private static final String LOADING =
            """
                        loading your previous tasks for you...
            ____________________________________________________________
            """;
    private static final String HELP = """
            Here is a list of valid commands ('-' is reserved for flags)
                [T][_]~ adds a ToDo task to your list
                [T][X]~ adds a marked ToDo task to your list
                [D][_]~ adds a Deadline task to your list
                [D][X]~ adds a marked Deadline task to your list
                    flags:
                        -by~    the specified deadline
                [E][_]~ adds an Event task to your list
                [E][X]~ adds a marked Event task to your list
                    flags:
                        -from~  start date/time
                        -to~    end date/time
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

    public static void main(String[] args) {
        greeting();
        loadTasks();
        readTasks(args);
        writeTasks();
    }

    public static void loadTasks() {
        try {
            File file = new File(FILENAME);
            if (file.createNewFile()) {
                System.out.println("Your file has been created");
            } else {
                loading();
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    storeTask(scanner.nextLine());
                }
                list();
            }
        } catch (IOException exception) {
            taskNotFound();
        }
    }

    public static void readTasks(String[] args) {
        for (String task : args) {
            System.out.println(task);

            if (task.startsWith("[h]") || task.startsWith("[H]")) {
                help();
            } else if (task.startsWith("[mark]")) {
                mark(task);
            } else if (task.startsWith("[unmark]")) {
                unmark(task);
            } else if (task.startsWith("[delete]")) {
                delete(task);
            } else if (task.startsWith("[list]")) {
                list();
            } else if (task.startsWith("[bye]")) {
                farewell();
            } else {
                storeTask(task);
            }
        }
    }

    public static void writeTasks() {
        try {
            FileWriter fileWriter = new FileWriter(FILENAME);
            String tasks = "";

            for (Task task : TASKS) {
                tasks += (task + System.lineSeparator());
            }

            fileWriter.write(tasks);
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println("An error has occurred");
        }
    }

    public static void greeting() {
        System.out.println(GREETING);
    }

    public static void farewell() {
        System.out.println(FAREWELL);
    }

    public static void loading() {
        System.out.println(LOADING);
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

    public static void mark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TASKS.get(index);
            task.mark();
            System.out.println("     Nice! I've marked this task as done:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
        } catch (RuntimeException IndexOutOfBoundsException) {
            taskNotFound();
        }
    }

    public static void unmark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TASKS.get(index);
            task.unmark();
            System.out.println("     OK, I've marked this task as not done yet:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
        } catch (RuntimeException IndexOutOfBoundsException) {
            taskNotFound();
        }
    }

    public static void delete(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TASKS.get(index);
            TASKS.remove(index);
            System.out.println("     Noted. I've removed this task:");
            echo(task);
        } catch (RuntimeException IndexOutOfBoundsException) {
            taskNotFound();
        }
    }

    public static void list() {
        System.out.println("     Here are the tasks in your list:");

        int index = 1;

        for (Task task : TASKS) {
            System.out.println("        " + index + "." + task.toString());
            index++;
        }

        System.out.println("____________________________________________________________");
    }

    public static void storeTask(String task) {
        if (task.startsWith("[T]")) {
            System.out.println("     ToDo task available:" );
            addToDoTask(task);
        } else if (task.startsWith("[D]")) {
            System.out.println("     Deadline task available:" );
            addDeadlineTask(task);
        } else if (task.startsWith("[E]")) {
            System.out.println("     Event task available:" );
            addEventTask(task);
        } else {
            invalidCommand();
        }
    }

    public static void addToDoTask(String input) {
        String name = getName(input);
        boolean isMarked = isMarked(input);
        Task task = new ToDoTask(name, isMarked);
        TASKS.add(task);
        echo(task);
    }

    public static void addDeadlineTask(String input) {
        String name = getName(input);
        boolean isMarked = isMarked(input);
        String deadline = getFlag(input, "-by.");
        Task task = new DeadlineTask(name, isMarked, deadline);
        TASKS.add(task);
        echo(task);
    }

    public static void addEventTask(String input) {
        String name = getName(input);
        boolean isMarked = isMarked(input);
        String startDateTime = getFlag(input, "-from.");
        String endDateTime = getFlag(input, "-to.");
        Task task = new EventTask(name, isMarked, startDateTime, endDateTime);
        TASKS.add(task);
        echo(task);
    }

    public static String getName(String input) {
        return input.substring(7).replaceAll(".-.*", "");
    }

    public static int getTaskNumber(String input) {
        return Integer.parseInt(input.replaceAll(".*]", "")) - 1;
    }

    public static String getFlag(String input, String flag) {
        try {
            return input.split(flag)[1].replaceAll(".-.*", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            invalidFlag();
        }
        return "";
    }

    public static boolean isMarked(String input) {
        return input.charAt(4) == 'X';
    }

    public static void echo(Task task) {
        String output =
            "        " + task + "\n" +
            "     Now you have " + TASKS.size() + " tasks in the list\n" +
            "____________________________________________________________";

        System.out.println(output);
    }
}
