package designpatterns.factory;

import java.util.Map;
import java.util.function.Supplier;

public class PaymentFactoryUsingLambda {
    private static final Map<String, Supplier<PaymentStrategy>> paymentMap = Map.of(
            "card", CreditCardPayment::new,
            "upi", UPIPayment::new);

    public static PaymentStrategy getPaymentMethod(String method) {
        Supplier<PaymentStrategy> paymentSupplier = paymentMap.get(method.toLowerCase());
        if (paymentSupplier == null) {
            throw new IllegalStateException("Payment method not implemented: " + method);
        }
        return paymentSupplier.get();
    }
}
