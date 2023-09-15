package domainModel;


public class Cloud {
    private Person from;

    public Cloud(Person from) {
        this.from = from;
    }

    public Person getFromWhom() {
        System.out.println("Эх, изначально это " + from.getName() + ", но теперь это облако газа!");
        return from;
    }
}