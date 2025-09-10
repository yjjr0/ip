package yapbot.taskmanager;

import yapbot.ui.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TaskList {
    private static final List<Task> TASKS = new ArrayList<>();

    public static Task addTask(Task task) {
        TASKS.add(task);
        return task;
    }

    public static Task getTask(int index) {
        return TASKS.get(index);
    }

    public static Task removeTask(int index) {
        return TASKS.remove(index);
    }

    public static List<Task> copy() {
        List<Task> copied = new ArrayList<>();

        for (Task task : TASKS) {
            copied.add(task);
        }

        return copied;
    }

    public static void clear() {
        TASKS.clear();
    }

    public static int numOfTasks() {
        return TASKS.size();
    }

    /**
     * Prints the tasks currently int the list
     */
    public static String listTasks() {
        return TASKS.stream()
                .map(Task::toString)
                .reduce("Here are the tasks in your list:\n", (response, task) -> response + task + "\n")
                + UI.lineBreak();
    }

    public static Stream<? extends Task> sort() {
        return TASKS.stream()
                .sorted(Task::compareTo);
    }

    public static String reminder() {
        return TaskList.sort()
                .map(Task::toString)
                .reduce("Here are the tasks that needs to be done first:\n", (response, task) -> response + task + "\n")
                + UI.lineBreak();
    }

    /**
     * Searches the task list and returns the tasks that matches with the specified keyword
     *
     * @return the list of tasks that matches with the specified keyword
     */
    public static String search(String keyword) {
        return TASKS.stream()
                .filter(task -> matching(task, keyword))
                .map(Task::toString)
                .reduce("Here are the tasks that matches with " + "'" + keyword + "'\n", (response, task) -> response + task + "\n")
                + UI.lineBreak();
    }

    /**
     * Checks if tasks contains the specified keyword
     *
     * @return true if the keyword is found, else false
     */
    private static boolean matching(Task task, String keyword) {
        return task.toString().contains(keyword);
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