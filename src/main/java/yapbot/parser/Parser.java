package yapbot.parser;

import yapbot.taskmanager.DeadlineTask;
import yapbot.taskmanager.EventTask;
import yapbot.taskmanager.Task;
import yapbot.taskmanager.TaskList;
import yapbot.taskmanager.ToDoTask;
import yapbot.ui.UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    /**
     * Reads data from remote storage (tasks.txt file) to store into local storage (TASKS arraylist)
     *
     * @param file the text file object to be loaded
     */
    public static void loadFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                storeTask(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            UI.invalidFile();
        }
    }

    /**
     * Processes the CLI input by the specified command
     *
     * @param task the CLI input from the user
     */
    public static void setTask(String task) {
        if (task.startsWith("[h]") || task.startsWith("[H]")) {
            UI.help();
        } else if (task.startsWith("[mark]")) {
            mark(task);
        } else if (task.startsWith("[unmark]")) {
            unmark(task);
        } else if (task.startsWith("[delete]")) {
            delete(task);
        } else if (task.startsWith("[find]")) {
            find(task);
        } else if (task.startsWith("[list]")) {
            list();
        } else if (task.startsWith("[bye]")) {
            UI.farewell();
        } else {
            storeTask(task);
        }
    }

    /**
     * Marks the task at the specified index as done
     *
     * @param input the CLI input from the user
     * @return true if the task is marked, else false
     */
    public static boolean mark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            task.mark();
            System.out.println("     Nice! I've marked this task as done:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
            return true;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Unmarks the task at the specified index as not done
     *
     * @param input the CLI input from the user
     * @return true if the task is unmarked, else false
     */
    public static boolean unmark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            task.unmark();
            System.out.println("     OK, I've marked this task as not done yet:\n" +
                    "        " + task + "\n" +
                    "____________________________________________________________");
            return true;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Deletes the task at the specified index
     *
     * @param input the CLI input from the user
     * @return true if the task is deleted, else false
     */
    public static boolean delete(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            TaskList.removeTask(index);
            System.out.println("     Noted. I've removed this task:");
            echo(task);
            return true;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Finds the task with the specified keyword
     *
     * @param input the CLI input from the user
     * @return true if the keyword is found, else false
     */
    public static boolean find(String input) {
        try {
            String keyword = getTaskDescription(input);
            System.out.println("     Here are the tasks that matches with " + "'" + keyword + "'");
            boolean isFound = TaskList.search(keyword);
            System.out.println("____________________________________________________________");
            return isFound;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Lists the tasks stored in the local storage
     */
    public static void list()
    {
        System.out.println("     Here are the tasks in your list:");
        TaskList.listTasks();
        System.out.println("____________________________________________________________");
    }

    /**
     * Filter by type and Stores the task into the local storage
     *
     * @param task the CLI input from the user
     */
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

    /**
     * Initialise a ToDoTask and Stores it into the local storage
     *
     * @param input the CLI input from the user
     */
    public static void addToDoTask(String input) {
        String name = getTaskDescription(input);
        boolean isMarked = isMarked(input);
        Task task = new ToDoTask(name, isMarked);
        TaskList.addTask(task);
        echo(task);
    }

    /**
     * Initialise a DeadlineTask and Stores it into the local storage
     *
     * @param input the CLI input from the user
     */
    public static void addDeadlineTask(String input) {
        String name = getTaskDescription(input);
        boolean isMarked = isMarked(input);
        String deadline = getFlag(input, "-by.");
        Task task = new DeadlineTask(name, isMarked, deadline);
        TaskList.addTask(task);
        echo(task);
    }

    /**
     * Initialise an EventTask and Stores it into the local storage
     *
     * @param input the CLI input from the user
     */
    public static void addEventTask(String input) {
        String name = getTaskDescription(input);
        boolean isMarked = isMarked(input);
        String startDateTime = getFlag(input, "-from.");
        String endDateTime = getFlag(input, "-to.");
        Task task = new EventTask(name, isMarked, startDateTime, endDateTime);
        TaskList.addTask(task);
        echo(task);
    }

    /**
     * Gets the task description from the CLI
     *
     * @param input the CLI input from the user
     * @return the task description
     */
    public static String getTaskDescription(String input) {
        return input.replaceAll("\\[.*\\].", "").replaceAll(".-.*", "");
    }

    /**
     * Gets the task number from the CLI
     *
     * @param input the CLI input from the user
     * @return the task number
     */
    public static int getTaskNumber(String input) {
        return Integer.parseInt(input.replaceAll("\\[.*\\].", "")) - 1;
    }

    /**
     * Gets the flag description from the CLI, if any
     *
     * @param input the CLI input from the user
     * @return the flag description, else an empty string
     */
    public static String getFlag(String input, String flag) {
        try {
            return input.split(flag)[1].replaceAll(".-.*", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            UI.invalidFlag();
        }
        return "";
    }

    /**
     * Gets the marked status of the task from the CLI
     *
     * @param input the CLI input from the user
     * @return the marked status of the task
     */
    public static boolean isMarked(String input) {
        return input.charAt(4) == 'X';
    }

    /**
     * Prints the task description to the terminal
     *
     * @param task the task object
     */
    public static void echo(Task task)
    {
        String output =
                "        " + task + "\n" +
                        "     Now you have " + TaskList.numOfTasks() + " tasks in the list\n" +
                        "____________________________________________________________";

        System.out.println(output);
    }
}
