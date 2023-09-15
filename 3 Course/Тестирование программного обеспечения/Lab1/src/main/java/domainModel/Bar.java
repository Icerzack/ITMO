package domainModel;

import java.util.ArrayList;

public class Bar {
    private String name;
    private String location;
    private ArrayList<Person> visitors;

    public Bar(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public void takeOrder(Person from) {
        System.out.println("Бар " + this.getName() + " взял заказ у " + from.getName());
        visitors.add(from);
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Person> getVisitors() {
        return visitors;
    }
}