package designpatterns.singleton.singletonv1;

public class Singletonv1 {

    private static Singletonv1 instance;

    private Singletonv1() {
        // private constructor to prevent instantiation
        System.out.println("Singletonv1 instance created");
    }

    // Public method to provide global access
    public static Singletonv1 getInstance() {
        // Create object only when needed (lazy loading)
        if (instance == null) {
            instance = new Singletonv1();
        }
        return instance;
    }
}
