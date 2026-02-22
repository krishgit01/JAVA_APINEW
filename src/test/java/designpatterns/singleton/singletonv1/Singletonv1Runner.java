package designpatterns.singleton.singletonv1;

public class Singletonv1Runner {
    public static void main(String[] args) {
        Singletonv1 instance1 = Singletonv1.getInstance();
        Singletonv1 instance2 = Singletonv1.getInstance();

        System.out.println("Instance 1: " + instance1);
        System.out.println("Instance 2: " + instance2);

        // Verify that both instances are the same
        if (instance1 == instance2) {
            System.out.println("Both instances are the same. Singleton pattern works!");
        } else {
            System.out.println("Instances are different. Singleton pattern failed.");
        }
    }
}

/*
⚠ Problem: Not Thread-Safe
If two threads call getInstance() at the same time:
Both may create separate objects ❌
So this version is unsafe in multi-threaded environments.
 */