// S010 - right.java (Developer B)
// Intent: add retry support WITHOUT breaking implementors -> used default
// methods and kept the ORIGINAL send() signature untouched.
public interface Notifier {

    void send(String messageId, String channel, String body);   // unchanged

    String getName();

    // Safe addition: default method, implementors unaffected.
    default void sendWithRetry(String messageId, String channel,
                               String body, int attempts) {
        for (int i = 0; i < Math.max(1, attempts); i++) {
            try {
                send(messageId, channel, body);
                return;                       // idempotent per contract
            } catch (RuntimeException e) {
                if (i == attempts - 1) throw e;
            }
        }
    }
}
