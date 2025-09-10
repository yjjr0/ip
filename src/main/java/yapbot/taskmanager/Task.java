package yapbot.taskmanager;

import java.time.LocalDate;

public class Task implements Comparable<Task> {
    private String name;
    private boolean isMarked;

    public Task(String name, boolean isMarked) {
        this.name = name;
        this.isMarked = isMarked;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public Task mark() {
        this.isMarked = true;
        return this;
    }

    public Task unmark() {
        this.isMarked = false;
        return this;
    }

    public LocalDate getISODate() {
        return LocalDate.now();
    }

    @Override
    public int compareTo(Task task) {
        if (this.getISODate().isBefore(task.getISODate())) {
            return 1;
        } else if (this.getISODate().isAfter(task.getISODate())) {
            return -1;
        }
        return 0;
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