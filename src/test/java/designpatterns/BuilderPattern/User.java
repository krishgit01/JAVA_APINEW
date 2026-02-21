package designpatterns.BuilderPattern;

// This is the main class we want to create
public class User {
    // Make fields final to make object immutable
    // Immutable = once created, cannot change
    private final String name;
    private final String email;
    private final int age;
    private final String country;
    private final boolean isActive;

    // Private constructor
    // Why private?
    // We don't want users to create object directly using new User(...)
    // We want them to use Builder
    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.country = builder.country;
        this.isActive = builder.isActive;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public static class Builder {

        private final String name; // required
        private final int age; // required

        // Optional fields - initialize with default values
        private String email = "not_provided"; // optional
        private String country = "unknown"; // optional
        private boolean isActive = false; // optional

        // Constructor for required fields
        // Why?
        // We force user to provide mandatory data
        public Builder(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Optional field method
        // Why return Builder?
        // To allow method chaining (Fluent Interface)
        public Builder email(String email) {
            this.email = email;
            return this; // return Builder to allow chaining
        }

        public Builder country(String country) {
            this.country = country;
            return this; // return Builder to allow chaining
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
            return this; // return Builder to allow chaining
        }

        public User build() {
            return new User(this); // pass Builder to User constructor
        }
    }
}
