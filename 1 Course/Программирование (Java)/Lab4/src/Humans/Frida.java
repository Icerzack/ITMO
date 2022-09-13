package Humans;

import Enums.Actions;
import Enums.Emotions;
import Exceptions.CanNotCaressException;
import Exceptions.RelationAbsenceException;
import Launch.Main;
import State.Action;
import State.Relation;

import java.util.HashMap;
import java.util.Objects;

public class Frida extends Main.Person implements Relation, Action {
    HashMap<String, String> map = new HashMap<>();

    public Frida(String name, int age) {
        super(name, age);
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
                System.out.println(this.getName() + " выступает на телевидении ");
                break;
            case BELIEVE:
                System.out.println(this.getName()+" не верила в это");
                break;
            case ALARM:
                System.out.println(this.getName()+" встревожилась");
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public void doDirectedAction(Actions what, Main.Person whom) throws CanNotCaressException {
        switch (what) {
            case SIT:
                System.out.println(this.getName()+" посидела с "+whom.getName());
                break;
            case LIVE:
                System.out.println(this.getName()+" живет с "+whom.getName());
                break;
            case TO_MAKE_A_MOVIE:
                System.out.println(this.getName()+" захотела сделать фильм о "+whom.getName());
                break;
            case COMFORT:
                System.out.println(this.getName()+" утешила "+whom.getName());
                break;
            case WARN:
                System.out.println(this.getName()+" предупредила "+whom.getName() + " об опасности");
                break;
            case CARESS:
                if (map.get(whom.getName()) == " любит "){
                    System.out.println(this.getName()+" похлопала по голове "+whom.getName());
                }
                else {
                    throw new CanNotCaressException("Невозможно выполнить действие 'погладить по голове' так как "+this.getName() + "не в хороших отношениях с "+whom.getName());
                }
                break;
            case INVITE:
                System.out.println(this.getName()+" пригласила "+whom.getName());
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public void getRelation(Main.Person withWhichPerson) {
        if(map.isEmpty()){
            throw new RelationAbsenceException("ОШИБКА -> У "+this.getName() + " нет существующих отношений");
        }
        else{
            System.out.println(this.getName()+" обнаружила, что "+map.get(withWhichPerson.getName())+withWhichPerson.getName());
        }
    }

    @Override
    public void setRelation(Emotions what, Main.Person withWhom) {
        switch (what) {
            case FOND:
                map.put(withWhom.getName(), " любит ");
                break;
            case ANGRY:
                map.put(withWhom.getName(), " злиться на ");
                break;
            case KIND:
                map.put(withWhom.getName(), " относится добрее к ");
            default:
                System.out.println("");
        }
    }
    @Override
    public String toString() {
        return "Humans.Frida{" +
                "map=" + map +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frida frida = (Frida) o;
        return Objects.equals(map, frida.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
/*
    @Override
    public String toString() {
        return "Humans.Frida{" +
                "map=" + map +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Humans.Frida frida = (Humans.Frida) o;
        return Objects.equals(map, frida.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }*/

