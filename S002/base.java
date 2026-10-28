// S002 - base.java
// Base version: a validator that must be called before saving.
public class OrderValidator {

    /**
     * Contract inherited by every caller: this method THROWS
     * IllegalArgumentException on invalid input, and returns true otherwise.
     * Callers rely on the exception to abort a transaction.
     */
    public boolean validate(String customerEmail, int quantity) {
        if (customerEmail == null || !customerEmail.contains("@")) {
            throw new IllegalArgumentException("bad email");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("bad quantity");
        }
        return true;
    }
}
