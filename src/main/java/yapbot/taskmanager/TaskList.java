package yapbot.taskmanager;

import yapbot.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private static final List<Task> TASKS = new ArrayList<>();

    /**
     * Adds the specified task into the list
     */
    public static void addTask(Task task) {
        TASKS.add(task);
    }

    /**
     * Gets the task at the specified index
     */
    public static Task getTask(int index) {
        return TASKS.get(index);
    }

    /**
     * Remove the task at the specified index
     */
    public static Task removeTask(int index) {
        return TASKS.remove(index);
    }

    /**
     * Prints the tasks currently int the list
     */
    public static String listTasks() {
        return TASKS.stream()
                .map(task -> task.toString() + "\n")
                .reduce("Here are the tasks in your list:\n", (response, task) -> response + task)
                + UI.lineBreak();
    }

    /**
     * Gets the number of tasks in the list
     * @return number of tasks in the list
     */
    public static int numOfTasks() {
        return TASKS.size();
    }

    /**
     * Searches the task list and returns the tasks that matches with the specified keyword
     * @return the list of tasks that matches with the specified keyword
     */
    public static String search(String keyword) {
        return TASKS.stream()
                .filter(task -> matching(task, keyword))
                .map(task -> task.toString() + "\n")
                .reduce("Here are the tasks that matches with " + "'" + keyword + "'\n", (response, task) -> response + task)
                + UI.lineBreak();
    }

    /**
     * Checks if tasks contains the specified keyword
     * @return true if the keyword is found, else false
     */
    private static boolean matching(Task task, String keyword) {
        return task.toString().contains(keyword);
    }

    /**
     * the testing version of String searchTest(keyword : String)
     * @return true if at least one match is found, else false
     */
    public static boolean searchTest(String keyword) {
        int index = 1;

        System.out.println("Here are the tasks that matches with " + "'" + keyword + "'\n");
        for (Task task : TASKS) {
            if (matching(task, keyword)) {
                System.out.println("        " + index + ". " + task.toString());
                index++;
            }
        }

        return index != 1;
    }

    /**
     * Gets the list of tasks as a single string
     */
    public static String getTasksAsTxt() {
        return TASKS.stream()
                .map(task -> task + System.lineSeparator())
                .reduce("", (response, task) -> response + task);
    }
}