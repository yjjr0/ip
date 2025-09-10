package yapbot.taskmanager;

import yapbot.ui.UI;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

public class DateTime {
    /**
     * Converts dates into ISO format
     *
     * @param date String format of the date
     * @return the date as a LocalDate object
     */
    public static LocalDate convertToISO(String date) {
        try {
            if (date.length() == 10) {
                return fromDayMonthYear(date);
            } else {
                return fromDayMonthNameYear(date);
            }
        } catch (IndexOutOfBoundsException exception) {
            UI.invalidDateFormat();
        } catch (NumberFormatException exception) {
            UI.invalidDateFormat();
        } catch (DateTimeParseException exception) {
            UI.invalidDateFormat();
        }
        return LocalDate.now();
    }

    /**
     * Converts dates from DD/MM/YYYY into ISO format
     *
     * @param date String format of the date
     * @return the date as a LocalDate object
     */
    public static LocalDate fromDayMonthYear(String date) throws IndexOutOfBoundsException {
        String[] DateFields = date.split("/");
        String day = DateFields[0];
        String month = DateFields[1];
        String year = DateFields[2];
        date = year + "-" + month + "-" + day;
        return LocalDate.parse(date);
    }

    /**
     * Converts dates from DD Month_Name YYYY into ISO format
     *
     * @param date String format of the date
     * @return the date as a LocalDate object
     */
    public static LocalDate fromDayMonthNameYear(String date) throws IndexOutOfBoundsException {
        String[] DateFields = date.split(" ");
        int day = Integer.parseInt(DateFields[0]);
        Month month = Month.valueOf(DateFields[1]);
        int year = Integer.parseInt(DateFields[2]);
        return LocalDate.of(year, month, day);
    }

    /**
     * Converts dates from ISO format to DD Month_Name YYYY
     *
     * @param date String format of the date
     * @return the date as a LocalDate object
     */
    public static String convertFromISO(LocalDate date) {
        int day = date.getDayOfMonth();
        Month month = date.getMonth();
        int year = date.getYear();
        return day + " " + month + " " + year;
    }
}
