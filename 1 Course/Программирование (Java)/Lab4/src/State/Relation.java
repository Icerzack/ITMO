package State;

import Enums.Emotions;
import Launch.Main;

public interface Relation {
    void getRelation(Main.Person withWhichPerson);
    void setRelation(Emotions what, Main.Person withWhom);
}
