public class Parser {
    public static void mark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            task.mark();
            System.out.println("     Nice! I've marked this task as done:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
        }
    }

    public static void unmark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            task.unmark();
            System.out.println("     OK, I've marked this task as not done yet:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
        }
    }

    public static void delete(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            TaskList.removeTask(index);
            System.out.println("     Noted. I've removed this task:");
            echo(task);
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
        }
    }

    public static void list() {
        System.out.println("     Here are the tasks in your list:");
        TaskList.listTasks();
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
            UI.invalidCommand();
        }
    }

    public static void addToDoTask(String input) {
        String name = getTaskName(input);
        boolean isMarked = isMarked(input);
        Task task = new ToDoTask(name, isMarked);
        TaskList.addTask(task);
        echo(task);
    }

    public static void addDeadlineTask(String input) {
        String name = getTaskName(input);
        boolean isMarked = isMarked(input);
        String deadline = getFlag(input, "-by.");
        Task task = new DeadlineTask(name, isMarked, deadline);
        TaskList.addTask(task);
        echo(task);
    }

    public static void addEventTask(String input) {
        String name = getTaskName(input);
        boolean isMarked = isMarked(input);
        String startDateTime = getFlag(input, "-from.");
        String endDateTime = getFlag(input, "-to.");
        Task task = new EventTask(name, isMarked, startDateTime, endDateTime);
        TaskList.addTask(task);
        echo(task);
    }

    public static String getTaskName(String input) {
        return input.replaceAll(".*].", "").replaceAll(".-.*", "");
    }

    public static int getTaskNumber(String input) {
        return Integer.parseInt(input.replaceAll(".*]", "")) - 1;
    }

    public static String getFlag(String input, String flag) {
        try {
            return input.split(flag)[1].replaceAll(".-.*", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            UI.invalidFlag();
        }
        return "";
    }

    public static boolean isMarked(String input) {
        return input.charAt(4) == 'X';
    }

    public static void echo(Task task) {
        String output =
                "        " + task + "\n" +
                        "     Now you have " + TaskList.numOfTasks() + " tasks in the list\n" +
                        "____________________________________________________________";

        System.out.println(output);
    }
}
