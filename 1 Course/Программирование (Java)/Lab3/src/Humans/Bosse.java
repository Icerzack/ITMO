package Humans;


import Enums.Actions;
import Enums.Emotions;
import State.Action;

import java.util.HashMap;
import java.util.Objects;

public class Bosse extends Person implements State.Relation, Action {
    HashMap<String, String> map = new HashMap<>();

    public Bosse(String name, int age) {
        super(name, age);
    }

    @Override
    public void getRelation(Person withWhichPerson) {
        System.out.println(this.getName() + " обнаружил, что " + map.get(withWhichPerson.getName()) + withWhichPerson.getName());
    }

    @Override
    public void setRelation(Emotions what, Person withWhom) {
        switch (what) {
            case FOND:
                map.put(withWhom.getName(), " любит ");
                break;
            case ANGRY:
                map.put(withWhom.getName(), " ненавидит ");
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
                System.out.println(this.getName() + " расхохотался");
                break;
            case MARVEL:
                System.out.println(this.getName() + " изумился");
                break;
            case EXPLAIN:
                System.out.println(this.getName() + " хотел было сказать");
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
                System.out.println(this.getName() + " посидел с " + whom.getName());
                break;
            case LIVE:
                System.out.println(this.getName() + " живет с " + whom.getName());
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bosse bosse = (Bosse) o;
        return Objects.equals(map, bosse.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return "Bosse{" +
                "map=" + map +
                '}';
    }
}
/*

    }*/

