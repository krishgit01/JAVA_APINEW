package designpatterns.singleton.Singletonv5;

public class Singletonv5Runner {

    public static void main(String[] args) {

        //Access Singleton instance and call method
        Singletonv5 singletonv51 = Singletonv5.INSTANCE;
        singletonv51.getMessage();

        // Modify state
        singletonv51.setMessage("Updated message");

        Singletonv5 singletonv52 = Singletonv5.INSTANCE;
        singletonv52.getMessage();

        // Check if both references are same
        System.out.println(singletonv51 == singletonv52); // true
    }
}
