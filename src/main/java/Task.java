public class Task {
    private String name;
    private boolean isMarked;

    public Task(String name, boolean isMarked) {
        this.name = name;
        this.isMarked = isMarked;
    }

    public void mark() {
        this.isMarked = true;
    }

    public void unmark() {
        this.isMarked = false;
    }

    @Override
    public String toString() {
        String status = this.isMarked ? "X" : " ";
        return String.format("[%s] %s", status, this.name);
    }
}
