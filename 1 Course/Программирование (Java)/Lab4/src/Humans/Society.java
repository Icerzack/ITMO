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

public class Society extends Main.Person implements Relation, Action {
    HashMap<String, String> map = new HashMap<>();

    public Society(String name, int age) {
        super(name, age);
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
                System.out.println(this.getName() + " расхохотались");
                break;
            case MARVEL:
                System.out.println(this.getName() + " изумились");
                break;
            case EXPLAIN:
                System.out.println(this.getName() + " хотели было сказать");
                break;
            case SPEAK_ON_TV:
                System.out.println(this.getName() + " выступили на телевидени ");
                break;
            case BELIEVE:
                System.out.println(this.getName()+" не верили в это");
                break;
            case ALARM:
                System.out.println(this.getName()+" встревожились");
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public void doDirectedAction(Actions what, Main.Person whom) throws CanNotCaressException {
        switch (what) {
            case SIT:
                System.out.println(this.getName()+" посидели с "+whom.getName());
                break;
            case LIVE:
                System.out.println(this.getName()+" живут с "+whom.getName());
                break;
            case TO_MAKE_A_MOVIE:
                System.out.println(this.getName()+" могли бы сделать фильм о "+whom.getName());
                break;
            case COMFORT:
                System.out.println(this.getName()+" утешили "+whom.getName());
                break;
            case WARN:
                System.out.println(this.getName()+" предупредили "+whom.getName() + " об опасности");
                break;
            case CARESS:
                if (map.get(whom.getName()) == " любят "){
                    System.out.println(this.getName()+" похлопали по голове "+whom.getName());
                }
                else {
                    throw new CanNotCaressException("Невозможно выполнить действие 'погладить по голове' так как "+this.getName() + "не в хороших отношениях с "+whom.getName());
                }
                break;
            case INVITE:
                System.out.println(this.getName()+" пригласили "+whom.getName());
                break;
            default:
                System.out.println("");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Society society = (Society) o;
        return Objects.equals(map, society.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return "Humans.Society{" +
                "map=" + map +
                '}';
    }
}