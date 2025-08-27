public class ToDoTask extends Task {
    public ToDoTask(String name, boolean isMarked) {
        super(name, isMarked);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
