// S009 - merge.java ==> TRAP: the two branches disagree about a GLOBAL
// convention, and the merge keeps BOTH conventions in ONE class — one per
// method. Off-by-one errors spread through every report.
import java.time.LocalDate;

public class DateRange {

    public static boolean contains(LocalDate start, LocalDate end, LocalDate day) {
        if (end.isBefore(start)) {                       // right's guard kept
            throw new IllegalArgumentException("empty range");
        }
        return !day.isBefore(start) && !day.isAfter(end); // LEFT's inclusive end
    }

    public static int lengthDays(LocalDate start, LocalDate end) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(start, end);
        // RIGHT's exclusive count kept -> now INCONSISTENT with contains()
    }

    public static boolean containsInclusive(LocalDate start, LocalDate end,
                                             LocalDate day) {
        return !day.isBefore(start) && !day.isAfter(end); // from right (helper)
    }
}

/*
 * INTERFERENCE / TRAPS:
 * TRAP 1: contains() says [start,end] but lengthDays() says [start,end).
 *         A 1-day vacation (Jan 5..Jan 5): contains=true, length=0 days.
 *         Payroll charges 0 days for a day HR marked as worked.
 * TRAP 2: right's containsInclusive() is now IDENTICAL to contains(), so
 *         reviewers conclude "no semantic change here" and skip the audit —
 *         while billing code that depended on exclusivity double-counts the
 *         boundary day of every invoice period.
 * TRAP 3: git showed conflicts only inside contains(); lengthDays() auto-merged
 *         to right's version because left's "+1" line and right's unchanged
 *         body touched adjacent-but-different hunks. Classic partial-resolve.
 */
