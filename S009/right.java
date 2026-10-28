// S009 - right.java (Developer B)
// Intent: keep the EXCLUSIVE convention (billing depends on it!) but make it
// explicit and add helpers. Also fixes off-by-one when end < start.
import java.time.LocalDate;

public class DateRange {

    /** True if `day` falls inside [start, end). End stays EXCLUSIVE. */
    public static boolean contains(LocalDate start, LocalDate end, LocalDate day) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("empty range");  // new guard
        }
        return !day.isBefore(start) && day.isBefore(end);
    }

    public static int lengthDays(LocalDate start, LocalDate end) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }

    // New helper for callers that really want inclusive semantics:
    public static boolean containsInclusive(LocalDate start, LocalDate end,
                                             LocalDate day) {
        return !day.isBefore(start) && !day.isAfter(end);
    }
}
