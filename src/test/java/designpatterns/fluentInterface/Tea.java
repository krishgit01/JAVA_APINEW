package designpatterns.fluentInterface;

// This class demonstrates Fluent Interface (Method Chaining)
// Each method returns the same object (this)
// So multiple methods can be chained together in a single statement
public class Tea {
    // Default values (initial state of object)
    // These act as base configuration
    private String teaType;
    private int sugarAmount = 1;
    private boolean milk = false;
    private int brewSeconds = 120;

    // Sets tea type
    // Returns same object to allow chaining
    public Tea setTeaType(String teaType) {
        this.teaType = teaType;  // Update current object's state
        return this;  // KEY: return same object for chaining
    }

    // Sets sugar level
    // Again returns same object to continue chain
    public Tea setSugarAmount(int sugarAmount) {
        this.sugarAmount = sugarAmount;  // Update current object's state
        return this;  // KEY: return same object for chaining
    }

    // Enables milk
    // No parameter needed since it's just turning milk ON
    public Tea addMilk() {
        this.milk = true;  // Update current object's state
        return this;  // KEY: return same object for chaining
    }

    public Tea setBrewTime(int seconds) {
        this.brewSeconds = seconds;  // Update current object's state
        return this;  // KEY: return same object for chaining
    }

    // Final action method
    // Prints the configured tea
    // Returning this is optional here,
    // but we return it to keep fluent style consistent
    public Tea serve() {
        System.out.println("Serving " + teaType +
                " tea with " + sugarAmount + " sugar(s), " + (milk ? "with milk" : "no milk") + ", brewed for " + brewSeconds + " seconds.");
        return this;  // Optional: return same object to allow further chaining if needed
    }
}
