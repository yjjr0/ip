public class EventTask extends Task{
    private String startDateTime;
    private String endDateTime;

    public EventTask(String name, String startDateTime, String endDateTime) {
        super(name);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.startDateTime, this.endDateTime);
    }
}
