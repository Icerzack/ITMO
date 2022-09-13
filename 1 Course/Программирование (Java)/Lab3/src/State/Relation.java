package State;

import Enums.Emotions;
import Humans.Person;

public interface Relation {
    void getRelation(Person withWhichPerson);
    void setRelation(Emotions what, Person withWhom);
}
