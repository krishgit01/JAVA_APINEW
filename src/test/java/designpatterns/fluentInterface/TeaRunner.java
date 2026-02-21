package designpatterns.fluentInterface;

public class TeaRunner {
    public static void main(String[] args) {
        // Example 1
        // Object is created
        // Then modified step-by-step using chaining
        Tea tea1 = new Tea()
                .setTeaType("Green")
                .addMilk()
                .setSugarAmount(2)
                .setBrewTime(300)
                .serve();

        Tea tea2 =new Tea()
                .setTeaType("Black")
                .setSugarAmount(1)
                .setBrewTime(180)
                .serve();
    }
}

/*
new Tea()
    .type("Masala")
    .sugar(2)
    .withMilk();
    Is actually equivalent to:

Tea temp = new Tea();
temp = temp.type("Masala");
temp = temp.sugar(2);
temp = temp.withMilk();

| Concept              | Why Used                      |
| -------------------- | ----------------------------- |
| `this`               | Refers to current object      |
| `return this`        | Enables method chaining       |
| Default field values | Provides base configuration   |
| Mutable object       | Values change during chaining |
| Fluent Interface     | Improves readability          |

🧩 Fluent Interface vs Builder
| Fluent Interface       | Builder                          |
| ---------------------- | -------------------------------- |
| Focuses on chaining    | Focuses on object construction   |
| Usually mutable        | Usually immutable                |
| Returns `this`         | Returns `Builder`                |
| Used for configuration | Used for complex object creation |

 */