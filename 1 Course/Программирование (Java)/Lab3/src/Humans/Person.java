package Humans;

import java.util.Objects;

public abstract class Person {

    private final int age;
    private final String name;

    Person(String name, int age){
        this.name=name;
        this.age=age;
    }

    public String getName() { return name; }
    public int getAge(){ return age; }
    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
/*
    */
}
