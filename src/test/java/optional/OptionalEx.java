package optional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OptionalEx {

    public static void main(String[] args) {
        List<Employee> employeeList = getEmployees();
        Optional<String> name = employeeList.stream()
                .map(Employee::getName)
                .filter(e -> e.startsWith("C"))
                .findFirst();

        System.out.println("name.isEmpty()" + name.isEmpty());
        System.out.println("name is : " + name.orElse("No name found"));

        List<Employee> employeeList1 = null;
        Optional<String> name1 = Optional.ofNullable(employeeList)
                .orElse(Collections.emptyList())
                .stream()
                .map(Employee::getName)
                .filter(e -> e.startsWith("C"))
                .findFirst();

        System.out.println("name1.isEmpty()" + name1.isEmpty());
        System.out.println("name1 is : " + name1.orElse("No name found"));
    }

    public static List<Employee> getEmployees() {
        return List.of(
                new Employee("Abraham", 30),
                new Employee("Olive", 25),
                new Employee("Jack", 28)
        );
    }
}

class Employee {
    String name;
    int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}