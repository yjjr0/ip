public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String name, boolean isMarked, String deadline) {
        super(name, isMarked);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D]%s -by %s", super.toString(), this.deadline);
    }
}
