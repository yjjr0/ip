import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {
    private LocalDate deadline;
    public DeadlineTask(String name, boolean isMarked, String deadline) {
        super(name, isMarked);
        this.deadline = formatDate(deadline);
    }

    // format date from DD/MM/YYYY to YYYY-MM-DD
    private LocalDate formatDate(String date) {
        try {
            String day = date.substring(0, 2);
            String month = date.substring(3, 5);
            String year = date.substring(6);
            date = year + "-" + month + "-" + day;
            return LocalDate.parse(date);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("invalid date format");
        } catch (DateTimeParseException exception) {
            System.out.println("invalid date format");
        }
        return LocalDate.now();
    }

    private String getDeadline() {
        int day = this.deadline.getDayOfMonth();
        Month month = this.deadline.getMonth();
        int year = this.deadline.getYear();
        return day + " " + month + " " + year;
    }

    @Override
    public String toString() {
        return String.format("[D]%s -by %s", super.toString(), getDeadline());
    }
}
