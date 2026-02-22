package designpatterns.factory;

public class CreditCardPayment implements PaymentStrategy{

    @Override
    public void pay(int amount) {
        System.out.println("Paying " + amount + " using Credit Card.");
    }
}
