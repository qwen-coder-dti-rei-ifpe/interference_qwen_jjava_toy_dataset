// S010 - merge.java ==> TRAP: the "keep both additions" interface merge that
// compiles in THIS file but silently breaks every implementation elsewhere.
//
// The merger saw two different send() declarations, concluded "left is newer,
// right is additive", and produced this union. javac on this file alone says
// OK. The explosion happens in the 40 plugin JARs compiled against base.
public interface Notifier {

    // LEFT's changed signature (priority inserted in the MIDDLE):
    void send(String messageId, int priority, String channel, String body);

    // RIGHT's original-signature method kept "for compatibility" — with a
    // default body so plugins that implemented only ONE send() still compile:
    default void send(String messageId, String channel, String body) {
        send(messageId, 0, channel, body);      // looks safe... or is it?
    }

    String getName();

    // LEFT's abstract requirement -> breaks ALL external implementors at
    // their own compile time; nobody notices here because this file has no
    // implements clause.
    int supportedMaxPriority();

    // RIGHT's retry helper kept verbatim:
    default void sendWithRetry(String messageId, String channel,
                               String body, int attempts) {
        for (int i = 0; i < Math.max(1, attempts); i++) {
            try {
                send(messageId, channel, body);   // binds to the DEFAULT above
                return;
            } catch (RuntimeException e) {
                if (i == attempts - 1) throw e;
            }
        }
    }
}

/*
 * INTERFERENCE / TRAPS:
 * TRAP 1: overload ambiguity by argument order. A plugin calling
 *         send(id, channel, body) now goes through the default wrapper which
 *         calls the ABSTRACT 4-arg send with priority=0 — but plugins that
 *         overrode the 3-arg method (as base required!) are NEVER invoked
 *         again: their business logic is bypassed silently at runtime.
 * TRAP 2: supportedMaxPriority() is abstract -> every existing plugin fails
 *         to compile on next build, yet CI here passes because the interface
 *         module builds alone. Deployment-order trap between modules.
 * TRAP 3: sendWithRetry's idempotency contract was written for base's send;
 *         after the merge retries re-enter through TWO layers of defaults,
 *         doubling actual network attempts under some overrides.
 */
