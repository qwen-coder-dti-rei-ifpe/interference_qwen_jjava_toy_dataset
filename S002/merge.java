// S002 - merge.java ==> TRAP: the classic "resolve by hand" semantic disaster.
//
// The merger saw a real conflict in validate() and "combined both sides":
// kept LEFT's no-throw philosophy (because it also kept looksValid()) and
// pasted RIGHT's new stock rule as an extra `if`. Result: compiles fine,
// every unit test that only checks "true/false" passes... but:
//   TRAP 1: callers that abort transactions via try/catch on
//           IllegalArgumentException now NEVER catch anything; invalid orders
//           are silently saved (return false is ignored by old call sites that
//           assumed boolean was always true or exception thrown).
//   TRAP 2: right's stricter e-mail regex was DROPPED without a trace
//           (looksValid still uses contains("@")).
//   TRAP 3: MAX_QUANTITY rule throws while everything around it returns
//           false -> two different failure languages in one method.
public class OrderValidator {

    public static final int MAX_QUANTITY = 100; // from right

    public static boolean looksValid(String customerEmail, int quantity) {
        return customerEmail != null
                && customerEmail.contains("@")   // left's weak check survived
                && quantity > 0;
    }

    public boolean validate(String customerEmail, int quantity) {
        if (!looksValid(customerEmail, quantity)) {
            System.out.println("validation failed (ignored)");
            return false;                        // no exception anymore!
        }
        if (quantity > MAX_QUANTITY) {
            throw new IllegalStateException("over stock limit"); // from right
        }
        return true;
    }
}

/*
 * INTERFERENCE NOTE: choosing "left's error strategy + right's business rule"
 * is exactly what a well-meaning human does when resolving this conflict.
 * Only the base file reveals that the ORIGINAL contract was exception-based.
 */
