package yapbot.taskmanager;

public class EventTask extends Task {
    private String startDateTime;
    private String endDateTime;

    public EventTask(String name, boolean isMarked, String startDateTime, String endDateTime) {
        super(name, isMarked);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Gets the current Event task as a string
     * @return the Event task description
     */
    @Override
    public String toString() {
        return String.format("[E]%s -from %s -to %s", super.toString(), this.startDateTime, this.endDateTime);
    }
}
