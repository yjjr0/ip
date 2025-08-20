public class Task {
    private String name;
    private boolean isMarked;

    public Task(String name) {
        this.name = name;
        this.isMarked = false;
    }

    public void mark() {
        this.isMarked = true;
    }

    public void unmark() {
        this.isMarked = false;
    }

    @Override
    public String toString() {
        String status = isMarked ? "X" : " ";
        return String.format("[%s] %s", status, name);
    }
}
