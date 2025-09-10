package yapbot.taskmanager;

import java.time.LocalDate;

public class EventTask extends Task {
    private LocalDate startDateTime;
    private LocalDate endDateTime;

    public EventTask(String name, boolean isMarked, String startDateTime, String endDateTime) {
        super(name, isMarked);
        this.startDateTime = DateTime.convertToISO(startDateTime);
        this.endDateTime = DateTime.convertToISO(endDateTime);
    }

    @Override
    public LocalDate getISODate() {
        return this.endDateTime;
    }

    private String getStartDateTime() {
        return DateTime.convertFromISO(this.startDateTime);
    }

    private String getEndDateTime() {
        return DateTime.convertFromISO(this.endDateTime);
    }

    /**
     * Gets the current Event task as a string
     * @return the Event task description
     */
    @Override
    public String toString() {
        return String.format("[E]%s -from %s -to %s", super.toString(), getStartDateTime(), getEndDateTime());
    }
}
