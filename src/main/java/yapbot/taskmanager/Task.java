package yapbot.taskmanager;

public class Task  {
    private String name;
    private boolean isMarked;

    public Task(String name, boolean isMarked) {
        this.name = name;
        this.isMarked = isMarked;
    }

    /**
     * Marks the current task as done
     */
    public void mark() {
        this.isMarked = true;
    }

    /**
     * Unmarks the current task as not done
     */
    public void unmark() {
        this.isMarked = false;
    }

    /**
     * Gets the current task as a string
     * @return the task description
     */
    @Override
    public String toString() {
        String status = this.isMarked ? "X" : " ";
        return String.format("[%s] %s", status, this.name);
    }
}