// S002 - left.java (Developer A)
// Intent: make validation "nicer" for the batch importer: never throw,
// just report problems. SIDE EFFECT: exception-based contract destroyed.
public class OrderValidator {

    // New helper everyone can use.
    public static boolean looksValid(String customerEmail, int quantity) {
        return customerEmail != null
                && customerEmail.contains("@")
                && quantity > 0;
    }

    public boolean validate(String customerEmail, int quantity) {
        // No more exceptions: log & return false.
        if (!looksValid(customerEmail, quantity)) {
            System.out.println("validation failed (ignored)");
            return false;
        }
        return true;
    }
}
