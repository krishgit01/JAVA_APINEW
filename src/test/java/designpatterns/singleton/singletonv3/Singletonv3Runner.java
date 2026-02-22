package designpatterns.singleton.singletonv3;

public class Singletonv3Runner {
    public static void main(String[] args) {
        Runnable task = () -> {
            Singletonv3 singleton = Singletonv3.getInstance();
            System.out.println("Threadname : " + Thread.currentThread().getName());
            System.out.println("Singletonv3 instance: " + singleton);
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();

        Singletonv3 instance1 = Singletonv3.getInstance();
        Singletonv3 instance2 = Singletonv3.getInstance();
        if(instance1==instance2){
            System.out.println("Both instances are the same. Singleton pattern works!");
        } else {
            System.out.println("Instances are different. Singleton pattern failed.");
        }
    }
}
