// S009 - left.java (Developer A)
// Intent: bugfix for "last day missing" complaints from HR module, which
// always passes inclusive ranges. Switch to [start, end] INCLUSIVE semantics.
import java.time.LocalDate;

public class DateRange {

    public static boolean contains(LocalDate start, LocalDate end, LocalDate day) {
        return !day.isBefore(start) && !day.isAfter(end);   // inclusive end
    }

    public static int lengthDays(LocalDate start, LocalDate end) {
        // inclusive count: Mon..Fri = 5
        return (int) java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
    }
}
