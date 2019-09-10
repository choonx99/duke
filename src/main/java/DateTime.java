import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date and time object to store and convert
 * date and time to a pleasant format e.g., 12/11/2019 1700
 * becomes 12th November 2019, 5:00pm.
 */
public class DateTime {
    private String date;
    private String time;

    public DateTime(String date, String time) {
        this.date = date;
        this.time = time;
    }

    /**
     * Creates a DateTime object out of a string representing date and time
     * e.g., 11/12/2019 1800 will give a DateTime object with date being
     * 11th December 2019 and time being 6:00pm
     *
     * @param dateTime String that represents a date and a time
     * @throws InvalidInputException occurs when an invalid date is input
     */
    public DateTime(String dateTime) throws InvalidInputException {
        String[] splitDateAndTime = dateTime.split(" ");
        String[] splitDate = splitDateAndTime[0].split("/");
        String day = splitDate[0];
        String month = splitDate[1];
        final String year = splitDate[2];

        try {
            String dateStr = splitDateAndTime[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("OOPS!!! The Date field is invalid");
        }

        if (day.equals("1") || day.equals("21") || day.equals("31")) {
            day = day + "st";
        } else if (day.equals("2") || day.equals("22")) {
            day = day + "nd";
        } else if (day.equals("3") || day.equals("23")) {
            day = day + "rd";
        } else {
            day = day + "th";
        }

        switch (month) {
        case "1":
            month = "January";
            break;
        case "2":
            month = "February";
            break;
        case "3":
            month = "March";
            break;
        case "4":
            month = "April";
            break;
        case "5":
            month = "May";
            break;
        case "6":
            month = "June";
            break;
        case "7":
            month = "July";
            break;
        case "8":
            month = "August";
            break;
        case "9":
            month = "September";
            break;
        case "10":
            month = "October";
            break;
        case "11":
            month = "November";
            break;
        case "12":
            month = "December";
            break;
        default:
            throw new InvalidInputException("OOPS!!! The Date/Time field is invalid");
        }

        this.date = day + " " + month + " " + year;

        if (splitDateAndTime.length == 1) {
            time = null;
        } else {
            String[] timings = splitDateAndTime[1].split("-");
            if (timings.length == 1) {
                String hour = splitDateAndTime[1].substring(0, 2);
                String mins = splitDateAndTime[1].substring(2);
                String period = "";

                if (hour.equals("00")) {
                    hour = "12";
                    period = "am";
                } else if (hour.equals("12")) {
                    period = "pm";
                } else {
                    if (Integer.parseInt(hour) > 12) {
                        period = "pm";
                        hour = (Integer.parseInt(hour) % 12) + "";
                    } else {
                        period = "am";
                    }
                }
                this.time = hour + ":" + mins + period;
            } else if (timings.length == 2) {
                String startHour = timings[0].substring(0, 2);
                String startMins = timings[0].substring(2);
                String endHour = timings[1].substring(0, 2);
                String endMins = timings[1].substring(2);
                String startPeriod = "";
                String endPeriod = "";

                if (startHour.equals("00")) {
                    startHour = "12";
                    startPeriod = "am";
                } else if (startHour.equals("12")) {
                    startPeriod = "pm";
                } else {
                    if (Integer.parseInt(startHour) > 12) {
                        startPeriod = "pm";
                        startHour = (Integer.parseInt(startHour) % 12) + "";
                    } else {
                        startPeriod = "am";
                    }
                }

                if (endHour.equals("00")) {
                    endHour = "12";
                    endPeriod = "am";
                } else if (endHour.equals("12")) {
                    endPeriod = "pm";
                } else {
                    if (Integer.parseInt(endHour) > 12) {
                        endPeriod = "pm";
                        endHour = (Integer.parseInt(endHour) % 12) + "";
                    } else {
                        endPeriod = "am";
                    }
                }
                this.time = startHour + ":" + startMins + startPeriod + "-" + endHour + ":" + endMins + endPeriod;
            }
        }
    }

    @Override
    public String toString() {
        if (time == null || time.equals("")) {
            return date;
        } else {
            return date + ", " + time;
        }
    }
}
