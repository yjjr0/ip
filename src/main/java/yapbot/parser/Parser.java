package yapbot.parser;

import yapbot.taskmanager.DeadlineTask;
import yapbot.taskmanager.EventTask;
import yapbot.taskmanager.Task;
import yapbot.taskmanager.TaskList;
import yapbot.taskmanager.ToDoTask;
import yapbot.ui.UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
     * Processes the command line input from the user
     *
     * @param task the command line input from the user
     * @return the response from the chatbot
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
        } else if (getCommand(task,"[update]")) {
            return update(task);
        } else if (getCommand(task,"[list]")) {
            return list();
        } else if (getCommand(task,"[reminder]")) {
            return reminder();
        } else if (getCommand(task,"[bye]")) {
            return UI.farewell();
        } else {
            return storeTask(task);
        }
    }

    /**
     * Filter by type and Stores the task into the local storage
     *
     * @param task the command line input from the user
     */
    public static String storeTask(String task) {
        if (getCommand(task,"[t]")) {
            return addToDoTask(task);
        } else if (getCommand(task,"[d]")) {
            return addDeadlineTask(task);
        } else if (getCommand(task,"[e]")) {
            return addEventTask(task);
        } else {
            return UI.invalidCommand();
        }
    }

    /**
     * Marks the task at the specified index as done
     *
     * @param input the command line input from the user
     * @return the task formated as a string
     */
    public static String mark(String input) {
        try {
            String[] indices = getTaskDescription(input).split(" ");

            return Arrays.stream(indices)
                    .map(index -> TaskList.getTask(getTaskNumber(index)))
                    .map(Task::mark)
                    .map(Task::toString)
                    .reduce("Nice! I've marked these tasks as done:\n", (response, task) -> response + task + "\n")
                    + UI.lineBreak();
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    /**
     * Unmarks the task at the specified index as not done
     *
     * @param input the command line input from the user
     * @return the task formated as a string
     */
    public static String unmark(String input) {
        try {
            String[] indices = getTaskDescription(input).split(" ");

            return Arrays.stream(indices)
                    .map(index -> TaskList.getTask(getTaskNumber(index)))
                    .map(Task::unmark)
                    .map(Task::toString)
                    .reduce("OK, I've marked these tasks as undone:\n", (response, task) -> response + task + "\n")
                    + UI.lineBreak();
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    /**
     * Deletes the task at the specified index
     *
     * @param input the command line input from the user
     * @return the task formated as a string
     */
    public static String delete(String input) {
        try {
            String[] indices = getTaskDescription(input).split(" ");
            List<Task> copy = TaskList.copy();
            TaskList.clear();

            return Stream.iterate(0, i -> i + 1)
                    .limit(copy.size())
                    .map(index -> filterTask(copy, indices, index))
                    .filter(task -> !task.isEmpty())
                    .reduce("Noted. I've removed these tasks:\n", (response, task) -> response + task + "\n")
                    + UI.lineBreak();
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    private static String filterTask(List<Task> copy, String[] indices, int taskNum) {
        if (contains(indices, taskNum)) {
            return copy.get(taskNum).toString();
        } else {
            TaskList.addTask(copy.get(taskNum));
            return "";
        }
    }

    private static boolean contains(String[] indices, int taskNum) {
        return Arrays.stream(indices)
                .map(Parser::getTaskNumber)
                .anyMatch(index -> index == taskNum);
    }

    /**
     * Finds all tasks that matches with the specified keyword
     *
     * @param input the command line input from the user
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
     * Updates the task name at the specified index
     *
     * @param input the command line input from the user
     * @return the task formated as a string
     */
    public static String update(String input) {
        try {
            int index = getTaskNumber(input);
            String newName = getFlag(input, "-to.");
            Task task = TaskList.getTask(index);
            assert task != null : UI.taskNotFound();

            task.setName(newName);
            return UI.updatedTask(task);
        } catch (RuntimeException IndexOutOfBoundsException) {
            return UI.taskNotFound();
        }
    }

    /**
     * Lists the tasks
     * * @return the task list formated as a string
     */
    public static String list()
    {
        String response = TaskList.listTasks();
        System.out.println(response);
        return response;
    }

    /**
     * Lists the tasks sorted in chronological order
     * * @return the task list formated as a string
     */
    public static String reminder() {
        String response = TaskList.reminder();
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
        return UI.addedTask(task);
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
        return UI.addedTask(task);
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
        return UI.addedTask(task);
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
     * @param index the CLI input from the user
     * @return the task number
     */
    public static int getTaskNumber(String index) {
        try {
            return Integer.parseInt(index) - 1;
        } catch (NumberFormatException exception) {
            UI.taskNotFound();
        }

        return 0;
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
