package designpatterns;

import java.util.function.Supplier;

public class SupplierInterface {
    public static void main(String[] args) {
        Supplier<String> stringSupplier = () -> "Hello, World!";
        System.out.println(stringSupplier.get());

        //explicitly casting the lambda expression to Supplier<String>
        System.out.println(((Supplier<String>)(()-> "Hello, World!")).get());
    }
}
