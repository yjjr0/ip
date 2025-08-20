import java.util.ArrayList;
import java.util.List;

public class YapBot {
    private static final String NAME = "YapBot";
    private static final List<Task> TASKS = new ArrayList<>();
    private static final String GREETING =
            "____________________________________________________________\n" +
            " Hello! I'm " + NAME + "\n" +
            " What can I do for you?\n" +
            "____________________________________________________________";

    private static final String FAREWELL =
            """
                         Bye. Hope to see you again soon!
            ____________________________________________________________
            """;

    public static void main(String[] args) {
        greeting();
        readInput(args);
    }

    public static void readInput(String[] args) {
        for (String input : args) {
            formatInput(input);

            mark(toMark(input));
            unmark(toUnmark(input));

            switch (input) {
                case "bye":
                    farewell();
                    break;
                case "list":
                    list();
                    break;
                default:
                    if (toMark(input) < 0 && toUnmark((input)) < 0) {
                        store(input);
                    }
            }
        }
    }

    public static void greeting() {
        System.out.println(GREETING);
    }

    public static void farewell() {
        System.out.println(FAREWELL);
    }

    public static void formatInput(String input) {
        String formatInput = "\n" + input + "\n" +
                "____________________________________________________________";
        System.out.println(formatInput);
    }

    public static int toMark(String input) {
        int index = -1;

        if (input.startsWith("mark")) {
            index = Integer.parseInt(input.substring(5,6)) - 1;
        }

        return index;
    }

    public static int toUnmark(String input) {
        int index = -1;

        if (input.startsWith("unmark")) {
            index = Integer.parseInt(input.substring(7, 8)) - 1;
        }

        return index;
    }

    public static void mark(int index) {
        if (index >= 0) {
            Task task = TASKS.get(index);
            task.mark();
            System.out.println("     Nice! I've marked this task as done:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
        }
    }

    public static void unmark(int index) {
        if (index >= 0) {
            Task task = TASKS.get(index);
            task.unmark();
            System.out.println("     OK, I've marked this task as not done yet:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
        }
    }

    public static void list() {
        int index = 1;

        for (Task task : TASKS) {
            System.out.println("     " + index + "." + task.toString());
            index++;
        }

        System.out.println("____________________________________________________________");
    }

    public static void store(String input) {
        TASKS.add(new Task(input));
        echo(input);
    }

    public static void echo(String input) {
        String output =
            "     added: " + input + "\n" +
            "____________________________________________________________";

        System.out.println(output);
    }
}
