// S002 - right.java (Developer B)
// Intent: tighten validation (more strict email + stock limit).
// Still honours the THROW contract; adds a new checked-style failure type.
public class OrderValidator {

    public static final int MAX_QUANTITY = 100;

    public boolean validate(String customerEmail, int quantity) {
        if (customerEmail == null
                || !customerEmail.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("bad email: " + customerEmail);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("bad quantity");
        }
        if (quantity > MAX_QUANTITY) {          // NEW business rule
            throw new IllegalStateException("over stock limit");
        }
        return true;
    }
}
