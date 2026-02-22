package designpatterns.factory;

public class UPIPayment implements PaymentStrategy {

    @Override
    public void pay(int amount) {
        System.out.println("Paying " + amount + " using UPI.");
    }
}
