// S005 - merge.java ==> TRAP: the "best of both worlds" concurrency merge
// that is actually WORSE than either branch.
//
// Merger reasoning: "left added synchronized, right added AtomicInteger and
// add(n) -> keep everything, obviously correct". Each line comes from a
// branch; the combination introduces a NEW bug neither branch had.
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private final AtomicInteger value = new AtomicInteger(0); // from right

    public synchronized void increment() {                    // left's keyword +
        value.incrementAndGet();                              // right's body
    }

    public void add(int n) {                                  // from right: NOT
        value.addAndGet(n);                                   // synchronized!
    }

    public int get() {
        return value.get();                                   // no modifier kept
    }
}

/*
 * INTERFERENCE / TRAPS:
 * TRAP 1: mixed locking discipline — increment() takes the monitor, add()
 *         does not. Subclasses or external code that synchronised on the
 *         instance (the BASE contract!) now get partial mutual exclusion:
 *         `synchronized(counter){ counter.add(5); ... }` races with any
 *         thread calling increment() outside the block? No — worse: readers
 *         that used to lock will still work, but code copied from LEFT that
 *         locks around read-modify-write of OTHER fields is now inconsistent.
 * TRAP 2: 'synchronized' on an atomic operation is redundant overhead that
 *         reviewers will "optimise away" later in ONE method only, silently
 *         breaking compound operations like check-then-add elsewhere.
 * TRAP 3: the field became final while base callers might reflectively reset
 *         it; and get()/add() lost left's synchronized, so the merged class
 *         has THREE different thread-safety stories in 12 lines.
 */
