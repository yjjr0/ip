import java.util.Objects;

public class YapBot {
    private static final String NAME = "YapBot";
    private static final String GREETING =
            "____________________________________________________________\n" +
            " Hello! I'm " + NAME + "\n" +
            " What can I do for you?\n" +
            "____________________________________________________________";

    private static final String FAREWELL =
            "     Bye. Hope to see you again soon!\n" +
            "____________________________________________________________\n";

    public static void main(String[] args) {
        greeting();
        readInput(args);
    }

    public static void readInput(String[] args) {
        for (String input : args) {
            formatInput(input);
            if (Objects.equals(input, "bye")) {
                farewell();
            } else {
                echo(input);
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

    public static void echo(String input) {
        String output =
            "     " + input + "\n" +
            "____________________________________________________________";

        System.out.println(output);
    }
}
