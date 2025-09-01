package yapbot.taskmanager;

public class ToDoTask extends Task {
    public ToDoTask(String name, boolean isMarked) {
        super(name, isMarked);
    }

    /**
     * Gets the current ToDo task as a string
     * @return the ToDo task description
     */
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
