package designpatterns.singleton.singletonv2;

public class Singletonv2 {
    private static Singletonv2 instance;

    private Singletonv2() {
        // private constructor to prevent instantiation
        System.out.println("Singletonv2 instance created");
    }

    public static synchronized Singletonv2 getInstance() {
        if (instance == null) {
            instance = new Singletonv2();
        }
        return instance;
    }
}


/*
 public static synchronized Singletonv2 getInstance()
 ⚠ This is slow because every call is synchronized
Even after the object is created.
We only need synchronization when object is null.
so the solution is
✅ Solution: Double Checked Locking
will see in singletonv3
 */