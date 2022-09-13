import Enums.Actions;
import Enums.Emotions;
import Humans.*;

public class Main {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static void main(String[] args){
        Bosse bosse = new Bosse("Боссе",10);
        FrekenBock frekenBock = new FrekenBock("Фрекен Бок", 10);
        Kid kid = new Kid("Малыш", 10);
        Bethan bethan = new Bethan("Бэтан", 10);
        Frida frida = new Frida("Фрида", 10);

        kid.setRelation(Emotions.ANGRY, frekenBock);
        kid.setRelation(Emotions.ANGRY, bosse);
        kid.setRelation(Emotions.FOND, bethan);

        kid.getRelation(frekenBock);
        kid.getRelation(bosse);
        kid.getRelation(bethan);

        bosse.doAction(Actions.LAUGH);

        kid.setRelation(Emotions.KIND, frekenBock);
        kid.doAction(Actions.EXPLAIN);
        kid.doDirectedAction(Actions.SIT, frekenBock);
        kid.doAction(Actions.MARVEL);
        kid.getRelation(frekenBock);

        frekenBock.doDirectedAction(Actions.LIVE, frida);

        frida.doAction(Actions.SPEAK_ON_TV);

    }
}
