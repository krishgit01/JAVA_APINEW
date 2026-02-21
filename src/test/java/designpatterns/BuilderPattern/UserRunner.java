package designpatterns.BuilderPattern;

public class UserRunner {
    public static void main(String[] args) {
        User user1 = new User.Builder("johndoe", 45)
                .email("test@test.com")
                .country("India")
                .isActive(true)
                .build();
        System.out.println("user1 value is: " + user1);

        //sets default values for email, country and isActive
        User user2 = new User.Builder("alex", 36).build();
        System.out.println("user2 value is: " + user2);
    }
}
