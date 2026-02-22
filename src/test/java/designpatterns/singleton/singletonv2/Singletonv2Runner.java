package designpatterns.singleton.singletonv2;

public class Singletonv2Runner {
    public static void main(String[] args) {
        Runnable task = () -> {
            Singletonv2 singleton = Singletonv2.getInstance();
            System.out.println("Threadname : " + Thread.currentThread().getName());
            System.out.println("Singletonv2 instance: " + singleton);
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();

        Singletonv2 instance1 = Singletonv2.getInstance();
        Singletonv2 instance2 = Singletonv2.getInstance();
        if(instance1==instance2){
            System.out.println("Both instances are the same. Singleton pattern works!");
        } else {
            System.out.println("Instances are different. Singleton pattern failed.");
        }

    }
}
