package State;

import Enums.Actions;
import Humans.Person;

public interface Action {
    void doAction(Actions what);
    void doDirectedAction(Actions what, Person whom);
}
