package domainModel;

public class Person {
    private String name;
    private int age;
    private Gender gender;
    private boolean isLaughing;

    public Person(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.isLaughing = false;
    }

    public void laughLoudly() {
        System.out.println(this.getName() + " громко смеется!");
        this.isLaughing = true;
    }

    public void stopLaughing() {
        System.out.println(this.getName() + " уже не смешно!");
        this.isLaughing = false;
    }

    public Cloud transformToCloud() {
        System.out.println(this.getName() + " превращается в облако из гидрогена, озона и оксида углерода!");
        return new Cloud(this);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isLaughing() {
        return isLaughing;
    }
}
