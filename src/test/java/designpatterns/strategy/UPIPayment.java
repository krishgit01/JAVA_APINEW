package designpatterns.strategy;

public class UPIPayment implements PaymentStrategy {
    private String upiId;

    public  UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paying " + amount + " using UPI with ID: " + upiId);
    }
}
