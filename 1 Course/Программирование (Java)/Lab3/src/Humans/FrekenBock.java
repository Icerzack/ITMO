package Humans;


import Enums.Actions;
import Enums.Emotions;
import State.Action;

import java.util.HashMap;
import java.util.Objects;

public class FrekenBock extends Person implements State.Relation, Action {
    HashMap<String, String> map = new HashMap<>();

    public FrekenBock(String name, int age) {
        super(name, age);
    }

    @Override
    public void getRelation(Person withWhichPerson) {
        System.out.println(this.getName() +" обнаружила, что "+ map.get(withWhichPerson.getName()) + withWhichPerson.getName());
    }

    @Override
    public void setRelation(Emotions what, Person withWhom) {
        switch (what) {
            case FOND:
                map.put(withWhom.getName(), " любит ");
                break;
            case ANGRY:
                map.put(withWhom.getName(), " злиться на ");
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
                System.out.println(this.getName() + " засмеялась");
                break;
            case MARVEL:
                System.out.println(this.getName() + " удивилась");
                break;
            case EXPLAIN:
                System.out.println(this.getName() + " думала рассказать");
                break;
            case SPEAK_ON_TV:
                System.out.println(this.getName() + " выступила на телевидени ");
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public void doDirectedAction(Actions what, Person whom) {
        switch (what) {
            case SIT:
                System.out.println(this.getName() + " посидела с " + whom.getName());
                break;
            case LIVE:
                System.out.println(this.getName() + " приходится жить с " + whom.getName());
                break;
            default:
                System.out.println("");
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrekenBock that = (FrekenBock) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return "FrekenBock{" +
                "map=" + map +
                '}';
    }
}
/*
    */

