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
     * Processes the command input from the user
     *
     * @param task the command input from the user
     */
    public static String setTask(String task) {
        if (getCommand(task, "[h]")) {
            return UI.help();
        } else if (getCommand(task,"[mark]")) {
            return mark(task);
        } else if (getCommand(task,"[unmark]")) {
            return unmark(task);
        } else if (getCommand(task,"[delete]")) {
            return delete(task);
        } else if (getCommand(task,"[find]")) {
            return find(task);
        } else if (getCommand(task,"[list]")) {
            return list();
        } else if (getCommand(task,"[bye]")) {
            return UI.farewell();
        } else {
            return storeTask(task);
        }
    }

    /**
     * Filter by type and Stores the task into the local storage
     *
     * @param task the CLI input from the user
     */
    public static String storeTask(String task) {
        if (getCommand(task,"[t]")) {
            System.out.println("ToDo task available:");
            return addToDoTask(task);
        } else if (getCommand(task,"[d]")) {
            System.out.println("Deadline task available:");
            return addDeadlineTask(task);
        } else if (getCommand(task,"[e]")) {
            System.out.println("Event task available:");
            return addEventTask(task);
        } else {
            return UI.invalidCommand();
        }
    }

    /**
     * Marks the task at the specified index as done
     *
     * @param input the command input from the user
     * @return the task formated as a string
     */
    public static String mark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            assert task != null : UI.taskNotFound();
            task.mark();
            return UI.markedTask(task);
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    /**
     * the testing version of String mark(input : String)
     *
     * @param input the command input from the user
     * @return true if the task is marked, else false
     */
    public static boolean markTest(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            task.mark();
            UI.markedTask(task);
            return true;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Unmarks the task at the specified index as not done
     *
     * @param input the command input from the user
     * @return the task formated as a string
     */
    public static String unmark(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            assert task != null : UI.taskNotFound();
            task.unmark();
            return UI.unmarkedTask(task);
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    /**
     * the testing version of String unmark(input : String)
     *
     * @param input the command input from the user
     * @return true if the task is unmarked, else false
     */
    public static boolean unmarkTest(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            assert task != null : UI.taskNotFound();
            task.unmark();
            UI.unmarkedTask(task);
            return true;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Deletes the task at the specified index
     *
     * @param input the command input from the user
     * @return the task formated as a string
     */
    public static String delete(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.removeTask(index);
            assert task != null : UI.taskNotFound();
            return UI.deletedTask(task);
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    /**
     * the testing version of String delete(input : String)
     *
     * @param input the command input from the user
     * @return true if the task is deleted, else false
     */
    public static boolean deleteTest(String input) {
        try {
            int index = getTaskNumber(input);
            Task task = TaskList.getTask(index);
            TaskList.removeTask(index);
            UI.deletedTask(task);
            return true;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Deletes the task at the specified index
     *
     * @param input the command input from the user
     * @return the task formated as a string
     */
    public static String find(String input) {
        try {
            String keyword = getTaskDescription(input);
            String response = TaskList.search(keyword);
            System.out.println(response);
            return response;
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    /**
     * Finds the task with the specified keyword
     *
     * @param input the CLI input from the user
     * @return true if the keyword is found, else false
     */
    public static boolean findTest(String input) {
        try {
            String keyword = getTaskDescription(input);
            boolean isFound = TaskList.searchTest(keyword);
            UI.lineBreak();
            return isFound;
        } catch (RuntimeException IndexOutOfBoundsException) {
            UI.taskNotFound();
            return false;
        }
    }

    /**
     * Lists the tasks stored in the local storage
     */
    public static String list()
    {
        String response = TaskList.listTasks();
        System.out.println(response);
        return response;
    }

    /**
     * Initialise a ToDoTask and Stores it into the local storage
     *
     * @param input the CLI input from the user
     */
    public static String addToDoTask(String input) {
        String name = getTaskDescription(input);
        boolean isMarked = isMarked(input);
        Task task = new ToDoTask(name, isMarked);
        TaskList.addTask(task);
        return UI.echo(task);
    }

    /**
     * Initialise a DeadlineTask and Stores it into the local storage
     *
     * @param input the CLI input from the user
     */
    public static String addDeadlineTask(String input) {
        String name = getTaskDescription(input);
        boolean isMarked = isMarked(input);
        String deadline = getFlag(input, "-by.");
        Task task = new DeadlineTask(name, isMarked, deadline);
        TaskList.addTask(task);
        return UI.echo(task);
    }

    /**
     * Initialise an EventTask and Stores it into the local storage
     *
     * @param input the CLI input from the user
     */
    public static String addEventTask(String input) {
        String name = getTaskDescription(input);
        boolean isMarked = isMarked(input);
        String startDateTime = getFlag(input, "-from.");
        String endDateTime = getFlag(input, "-to.");
        Task task = new EventTask(name, isMarked, startDateTime, endDateTime);
        TaskList.addTask(task);
        return UI.echo(task);
    }

    /**
     * Gets the task description from the CLI
     *
     * @param input the CLI input from the user
     * @return the task description
     */
    public static boolean getCommand(String input, String command) {
        return input.toLowerCase().startsWith(command);
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
}
