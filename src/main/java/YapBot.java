import java.util.ArrayList;
import java.util.List;

public class YapBot {
    private static final String NAME = "YapBot";
    private static final List<Task> TASKS = new ArrayList<>();
    private static final String GREETING =
            "____________________________________________________________\n" +
            "     Hello! I'm " + NAME + "\n" +
            "     What can I do for you?\n" +
            "____________________________________________________________";
    private static final String FAREWELL =
            """
                         Bye. Hope to see you again soon!
            ____________________________________________________________
            """;
    private static final String INVALIDCOMMAND = """
               OOPS!!! I'm sorry, but I don't know what that means :-(
            ____________________________________________________________
            """;
    private static final String TASKNOTFOUND = """
               OOPS!!! I'm sorry, the task could not be located :-(
            ____________________________________________________________
            """;

    public static void main(String[] args) {
        greeting();
        readTasks(args);
    }

    public static void readTasks(String[] args) {
        for (String input : args) {
            formatInput(input);

            if (input.startsWith("mark")) {
                mark(input);
            } else if (input.startsWith("unmark")) {
                unmark(input);
            } else if (input.startsWith("delete")) {
                delete(input);
            } else if (input.startsWith("bye")) {
                farewell();
            } else if (input.startsWith("list")) {
                list();
            } else {
                storeTask(input);
            }
        }
    }

    public static void greeting() {
        System.out.println(GREETING);
    }

    public static void farewell() {
        System.out.println(FAREWELL);
    }

    public static void invalidCommand() {
        System.out.println(INVALIDCOMMAND);
    }

    public static void taskNotFound() {
        System.out.println(TASKNOTFOUND);
    }

    public static void formatInput(String input) {
        String formatInput = "\n" + input + "\n" +
                "____________________________________________________________";
        System.out.println(formatInput);
    }

    public static void mark(String input) {
        try {
            int index = Integer.parseInt(input.substring(5,6)) - 1;
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
            int index = Integer.parseInt(input.substring(7, 8)) - 1;
            Task task = TASKS.get(index);
            task.unmark();
            System.out.println("     OK, I've marked this task as not done yet:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
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

    public static void storeTask(String input) {
        if (input.startsWith("todo")) {
            System.out.println("     Got it. I've added this ToDo task:" );
            addToDoTask(input);
        } else if (input.startsWith("deadline")) {
            System.out.println("     Got it. I've added this Deadline task:" );
            addDeadlineTask(input);
        } else if (input.startsWith("event")) {
            System.out.println("     Got it. I've added this Event task:" );
            addEventTask(input);
        } else {
            invalidCommand();
        }
    }

    public static void addToDoTask(String input) {
        String name = input.substring(5);
        Task task = new ToDoTask(name);
        TASKS.add(task);
        echo(task);
    }

    public static void addDeadlineTask(String input) {
        String[] fields = input.split("/by");
        String name = fields[0].substring(9);
        String deadline = fields[1].substring(1);
        Task task = new DeadlineTask(name, deadline);
        TASKS.add(task);
        echo(task);
    }

    public static void addEventTask(String input) {
        String[] fields = input.split("/from");
        String name = fields[0].substring(6);
        String startDateTime = fields[1].split("/to")[0].substring(1);
        String endDateTime = fields[1].split("/to")[1].substring(1);
        Task task = new EventTask(name, startDateTime, endDateTime);
        TASKS.add(task);
        echo(task);
    }

    public static void delete(String input) {
        try {
            int index = Integer.parseInt(input.substring(7,8)) - 1;
            Task task = TASKS.get(index);
            TASKS.remove(index);
            System.out.println("     Noted. I've removed this task:");
            echo(task);
        } catch (RuntimeException IndexOutOfBoundsException) {
            taskNotFound();
        }
    }

    public static void echo(Task task) {
        String output =
            "        " + task + "\n" +
            "     Now you have " + TASKS.size() + " tasks in the list\n" +
            "____________________________________________________________";

        System.out.println(output);
    }
}
