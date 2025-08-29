package yapbot.taskmanager;

import yapbot.ui.UI;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

public class DateTime {
    public static LocalDate convertToISO(String date)
    {
        try
        {
            if (date.length() == 10)
            {
                return fromDayMonthYear(date);
            } else
            {
                return fromDayMonthNameYear(date);
            }
        } catch (IndexOutOfBoundsException exception)
        {
            UI.invalidDateFormat();
        } catch (NumberFormatException exception)
        {
            UI.invalidDateFormat();
        } catch (DateTimeParseException exception)
        {
            UI.invalidDateFormat();
        }
        return LocalDate.now();
    }

    // format date from DD/MM/YYYY to YYYY-MM-DD
    public static LocalDate fromDayMonthYear(String date) throws IndexOutOfBoundsException
    {
        String[] DateFields = date.split("/");
        String day = DateFields[0];
        String month = DateFields[1];
        String year = DateFields[2];
        date = year + "-" + month + "-" + day;
        return LocalDate.parse(date);
    }

    // format date from DD Month_Name YYYY to YYYY-MM-DD
    public static LocalDate fromDayMonthNameYear(String date) throws IndexOutOfBoundsException
    {
        String[] DateFields = date.split(" ");
        int day = Integer.parseInt(DateFields[0]);
        Month month = Month.valueOf(DateFields[1]);
        int year = Integer.parseInt(DateFields[2]);
        return LocalDate.of(year, month, day);
    }
}
