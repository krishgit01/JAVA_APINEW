package designpatterns.factory;

// Factory class responsible for object creation
public class PaymentFactory {

    // Static method so we don't need factory object
    public static PaymentStrategy getPaymentMethod(String method) {
        if ("CARD".equalsIgnoreCase(method)) {
            return new CreditCardPayment();
        } else if ("UPI".equalsIgnoreCase(method)) {
            return new UPIPayment();
        } else {
            throw new IllegalArgumentException("Unknown payment method: " + method);
        }
    }
}
