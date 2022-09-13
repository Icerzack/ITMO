package Humans;

import Enums.Actions;
import Enums.Emotions;
import State.Action;

import java.util.HashMap;
import java.util.Objects;

public class Kid extends Person implements State.Relation, Action {
    HashMap<String, String> map = new HashMap<>();

    public Kid(String name, int age) {
        super(name, age);
    }

    @Override
    public void getRelation(Person withWhichPerson) {
        System.out.println(this.getName() +" обнаружил, что "+ map.get(withWhichPerson.getName()) + withWhichPerson.getName());
    }

    @Override
    public void setRelation(Emotions what, Person withWhom) {
        switch (what) {
            case FOND:
                map.put(withWhom.getName(), " начал любить ");
                break;
            case ANGRY:
                map.put(withWhom.getName(), " сердится на ");
                break;
            case KIND:
                map.put(withWhom.getName(), " относится добрее к ");
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public void doAction(Actions what) {
        switch (what) {
            case LAUGH:
                System.out.println(this.getName() + " захихикал");
                break;
            case MARVEL:
                System.out.println(this.getName() + " изумился");
                break;
            case EXPLAIN:
                System.out.println(this.getName() + " было начал оправдываться");
                break;
            case SPEAK_ON_TV:
                System.out.println(this.getName() + " выступил на телевидени ");
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public void doDirectedAction(Actions what, Person whom) {
        switch (what) {
            case SIT:
                System.out.println(this.getName() + " просидел какое-то время с " + whom.getName());
                break;
            case LIVE:
                System.out.println(this.getName() + " живет с " + whom.getName());
                break;
            default:
                System.out.println("");
        }
    }
    @Override
    public String toString() {
        return "Kid{" +
                "map=" + map +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kid kid = (Kid) o;
        return Objects.equals(map, kid.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
/*
    */

