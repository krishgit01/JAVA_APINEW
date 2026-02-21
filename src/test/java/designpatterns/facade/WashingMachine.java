package designpatterns.facade;


// 2. THE FACADE - The "One-Button" Interface
public class WashingMachine {
    private DrumMotor drumMotor;
    private WaterPump waterPump;
    private Heater heater;

    // CORRECTED: We pass the components into the constructor.
    // This is called "Dependency Injection".
    public WashingMachine(DrumMotor drumMotor, WaterPump waterPump, Heater heater) {
        this.drumMotor = drumMotor;
        this.waterPump = waterPump;
        this.heater = heater;
    }

    // This method hides all the complexity from the user.
    public void startWash() {
        System.out.println("Starting wash cycle...");
        waterPump.filterWater();
        heater.heatWater();
        drumMotor.rotateSlow();
        waterPump.drainWater();
        drumMotor.rotateFast();
        System.out.println("Wash cycle completed.");
    }
}
