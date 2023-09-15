package domainModel;

public class Stranger extends Person{

    private Girl girl;

    public Stranger(String name, int age, Gender gender) {
        super(name, age, gender);

    }

    public void bringGirl(Girl girl) {
        System.out.println(this.getName() + " заволок " + girl.getName() + " в бар.");
        this.girl = girl;
    }

    public Girl getGirl() {
        return girl;
    }
}