package designpatterns.singleton.singletonv1;

public class Singletonv1RunnerThread {
    public static void main(String[] args) {
        Runnable task = () -> {
            Singletonv1 singletonv1 = Singletonv1.getInstance();
            System.out.println(Thread.currentThread().getName() + " -> Singletonv1 instance: " + singletonv1);
            System.out.println("Singletonv1 instance hashcode: " + singletonv1.hashCode());
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();
    }
}

/*
Two different memory addresses → two objects created ❌
if (instance == null)
Is NOT protected.
Two threads can enter it simultaneously.

instance = new Singletonv1();
Is NOT atomic.
It actually happens in 3 steps internally:
Allocate memory
Initialize object
Assign reference to instance
Thread interference can happen between these steps.

how to fix it?
Use synchronized. that will see in Singletonv2
public static synchronized Singleton getInstance() {

    if (instance == null) {
        instance = new Singleton();
    }

    return instance;
}
 */
