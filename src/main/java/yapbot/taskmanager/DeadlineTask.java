package yapbot.taskmanager;

import java.time.LocalDate;
import java.time.Month;

public class DeadlineTask extends Task
{
    private LocalDate deadline;

    public DeadlineTask(String name, boolean isMarked, String deadline)
    {
        super(name, isMarked);
        this.deadline = DateTime.convertToISO(deadline);
    }

    private String getDeadline()
    {
        int day = this.deadline.getDayOfMonth();
        Month month = this.deadline.getMonth();
        int year = this.deadline.getYear();
        return day + " " + month + " " + year;
    }

    @Override
    public String toString()
    {
        return String.format("[D]%s -by %s", super.toString(), getDeadline());
    }
}
