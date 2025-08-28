import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private static final List<Task> TASKS = new ArrayList<>();

    public static void addTask(Task task) {
        TASKS.add(task);
    }

    public static Task getTask(int index) {
        return TASKS.get(index);
    }

    public static void removeTask(int index) {
        TASKS.remove(index);
    }

    public static void listTasks() {
        int index = 1;

        for (Task task : TASKS) {
            System.out.println("        " + index + ". " + task.toString());
            index++;
        }
    }

    public static int numOfTasks() {
        return TASKS.size();
    }

    public static String getTasksAsTXT() {
        String tasks = "";

        for (Task task : TASKS) {
            tasks += (task + System.lineSeparator());
        }

        return tasks;
    }
}