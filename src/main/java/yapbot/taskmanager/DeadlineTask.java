package yapbot.taskmanager;

import java.time.LocalDate;

public class DeadlineTask extends Task {
    private LocalDate deadline;

    public DeadlineTask(String name, boolean isMarked, String deadline) {
        super(name, isMarked);
        this.deadline = DateTime.convertToISO(deadline);
    }

    @Override
    public LocalDate getISODate() {
        return this.deadline;
    }

    /**
     * Gets the deadline in DD Month_Name YYYY Format
     * @return the deadline in DD Month_Name YYYY Format
     */
    private String getDeadline() {
        return DateTime.convertFromISO(this.deadline);
    }

    /**
     * Gets the current Deadline task as a string
     * @return the Deadline task description
     */
    @Override
    public String toString() {
        return String.format("[D]%s -by %s", super.toString(), getDeadline());
    }
}
