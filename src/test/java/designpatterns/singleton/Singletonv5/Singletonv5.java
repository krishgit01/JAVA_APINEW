package designpatterns.singleton.Singletonv5;

public enum Singletonv5 {
    INSTANCE;

    private String message;

    // Constructor (automatically private in enum)
    private Singletonv5() {
        // private constructor to prevent instantiation
        System.out.println("Singletonv5 instance created");
        message = "Hello from Singletonv5!";
    }

    public void getMessage() {
        System.out.println("message value is : " + message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
