// S010 - base.java
// Base version: interface implemented by many plugins across the company.
public interface Notifier {

    /** Send a notification. Implementations must be idempotent per messageId. */
    void send(String messageId, String channel, String body);

    /** Human-readable name used in logs and admin UI. */
    String getName();
}
