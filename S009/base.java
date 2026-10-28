// S009 - base.java
// Base version: date range helper used by reports. Convention documented in
// the team wiki: ALL intervals are [start, end) — end EXCLUSIVE.
import java.time.LocalDate;

public class DateRange {

    /** True if `day` falls inside [start, end). End is EXCLUSIVE. */
    public static boolean contains(LocalDate start, LocalDate end, LocalDate day) {
        return !day.isBefore(start) && day.isBefore(end);
    }

    /** Number of days in [start, end). */
    public static int lengthDays(LocalDate start, LocalDate end) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }
}
