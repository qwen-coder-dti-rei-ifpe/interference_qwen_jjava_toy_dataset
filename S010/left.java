// S010 - left.java (Developer A)
// Intent: add priority support to every notifier. Chose to EXTEND THE
// INTERFACE with a new abstract method AND change send()'s signature.
// NOTE: this breaks every external implementation (compiled elsewhere).
public interface Notifier {

    // Signature changed: priority added in the MIDDLE of the parameter list.
    void send(String messageId, int priority, String channel, String body);

    String getName();

    // New ABSTRACT method -> all existing implementors fail to compile until
    // they add it; team assumed "default methods" would save them, but forgot
    // one method here.
    int supportedMaxPriority();
}
