package yapbot.taskmanager;

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
    public static void removeTask(int index) {
        TASKS.remove(index);
    }

    /**
     * Prints the tasks currently int the list
     */
    public static void listTasks() {
        int index = 1;

        for (Task task : TASKS) {
            System.out.println("        " + index + ". " + task.toString());
            index++;
        }
    }

    /**
     * Gets the number of tasks in the list
     * @return number of tasks in the list
     */
    public static int numOfTasks() {
        return TASKS.size();
    }

    /**
     * Searches and prints the tasks that matches with the specified keyword
     * @return true if at least one match is found, else false
     */
    public static boolean search(String keyword) {
        int index = 1;

        for (Task task : TASKS) {
            if (matching(task, keyword)) {
                System.out.println("        " + index + ". " + task.toString());
                index++;
            }
        }

        return index != 1;
    }

    /**
     * Checks if tasks contains the specified keyword
     * @return true if the keyword is found, else false
     */
    private static boolean matching(Task task, String keyword) {
        return task.toString().contains(keyword);
    }

    /**
     * Gets the list of tasks as a single string
     */
    public static String getTasksAsTxt() {
        String tasks = "";

        for (Task task : TASKS) {
            tasks += (task + System.lineSeparator());
        }

        return tasks;
    }
}