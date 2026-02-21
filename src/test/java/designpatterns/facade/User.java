package designpatterns.facade;

public class User {
    public static void main(String[] args) {
        // Create the individual parts
        DrumMotor drumMotor = new DrumMotor();
        Heater heater = new Heater();
        WaterPump waterPump = new WaterPump();

        // Put them into the Facade
        WashingMachine washingMachine = new WashingMachine(drumMotor, waterPump, heater);
        // The user only sees this one simple command
        washingMachine.startWash();
    }
}
