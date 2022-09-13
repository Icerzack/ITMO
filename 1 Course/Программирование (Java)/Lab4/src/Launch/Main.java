package Launch;

import Enums.Actions;
import Enums.Emotions;
import Exceptions.CanNotCaressException;
import Humans.*;
import State.Action;

import java.util.Objects;

public class Main {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static void main(String[] args){
        PeopleInitializer pi = new Main().new PeopleInitializer();
        pi.initializeTheEnvironment();
    }
    public static class Person {

        private final int age;
        private final String name;

        public Person(String name, int age){
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
    public class PeopleInitializer{
        public void initializeTheEnvironment(){
            Bosse bosse = new Bosse("Боссе",10);
            FrekenBock frekenBock = new FrekenBock("Фрекен Бок", 10);
            Kid kid = new Kid("Малыш", 10);
            Bethan bethan = new Bethan("Бэтан", 10);
            Frida frida = new Frida("Фрида", 10);
            Carlson carlson = new Carlson("Карлсон", 10);
            Dad dad = new Dad("Папа", 40);
            Society society = new Society("Гости", 500);

            Thread thread = new Thread(new Runnable() { // ИСПОЛЬЗОВАНИЕ ОТДЕЛЬНОГО ПОТОКА В КАЧЕСТВЕ ЛОКАЛЬНОГО И АНОНИМНОГО КЛАССА
                @Override
                public void run() {
                    kid.setRelation(Emotions.ANGRY, frekenBock);
                    kid.setRelation(Emotions.ANGRY, bosse);
                    kid.setRelation(Emotions.FOND, bethan);
                    System.out.println("");
                    bethan.setRelation(Emotions.FOND, kid); //ДЛЯ ПРИМЕРА СТАВИМ ОТНОШЕНИЕ БЭТАН И МАЛЫША КАК "ЛЮБИТ"
                    bethan.getRelation(kid);
                    try {
                        bethan.doDirectedAction(Actions.CARESS, kid); // ТАК КАК БЭТАН ЛЮБИТ МАЛЫША, ТО ОНА ХОЧЕТ И МОЖЕТ ЕГО ПОХОПАТЬ ПО ГОЛОВЕ
                    } catch (CanNotCaressException e) {
                        System.err.println(e.getMessage());
                    }
                    System.out.println("");
                    kid.doAction(Actions.BELIEVE);
                    dad.doAction(Actions.BELIEVE);
                    System.out.println("");
                    try {
                        bosse.doDirectedAction(Actions.COMFORT, kid);
                    } catch (CanNotCaressException e) {
                        e.printStackTrace();
                    }

                    bosse.doAction(Actions.LAUGH);
                    System.out.println("");
                    kid.setRelation(Emotions.KIND, frekenBock);
                    kid.doAction(Actions.EXPLAIN);
                    try {
                        kid.doDirectedAction(Actions.SIT, frekenBock);
                    } catch (CanNotCaressException e) {
                        System.err.println(e.getMessage());
                    }
                    kid.doAction(Actions.MARVEL);
                    kid.getRelation(frekenBock);
                    System.out.println("");
                    try {
                        frekenBock.doDirectedAction(Actions.LIVE, frida);
                    } catch (CanNotCaressException e) {
                        System.err.println(e.getMessage());
                    }
                    System.out.println("");
                    frida.doAction(Actions.SPEAK_ON_TV);
                    System.out.println("");
                    try {
                        frekenBock.doDirectedAction(Actions.LIVE, kid);
                    } catch (CanNotCaressException e) {
                        e.printStackTrace();
                    }

                    frekenBock.doAction(Actions.EXPLAIN);
                    System.out.println("");
                    kid.doAction(Actions.ALARM);
                    System.out.println("");
                    try {
                        frekenBock.doDirectedAction(Actions.INVITE, society);
                    } catch (CanNotCaressException e) {
                        e.printStackTrace();
                    }
                    System.out.println("");
                    try {
                        society.doDirectedAction(Actions.TO_MAKE_A_MOVIE, carlson);
                    } catch (CanNotCaressException e) {
                        e.printStackTrace();
                    }
                    System.out.println("");
                    try {
                        kid.doDirectedAction(Actions.WARN, carlson);
                    } catch (CanNotCaressException e) {
                        e.printStackTrace();
                    }
                    System.out.println("");
                    dad.getRelation(bosse); // ДЛЯ ПРИМЕРА ПОПРОБУЕ ПОЛУЧИТЬ ОТНОШЕНИЕ ПАПЫ И ЕГО ДОЧКИ БОССЕ - ТАК ЕГО МЫ НЕ ЗАДАЛИ ИЗНАЧАЛЬНО ТО БУДЕТ RUNTIME ERROR.

                }
            });
            thread.start();
        }
    }
}
