package designpatterns.singleton.singletonv4;

public class Singletonv4Runner {
    public static void main(String[] args) {
        Singletonv4 instance1 = Singletonv4.getInstance();
        Singletonv4 instance2 = Singletonv4.getInstance();

        System.out.println("Instance 1: " + instance1);
        System.out.println("Instance 2: " + instance2);
        if(instance1==instance2){
            System.out.println("Both instances are the same. Singleton pattern works!");
        } else {
            System.out.println("Instances are different. Singleton pattern failed.");
        }
    }
}
