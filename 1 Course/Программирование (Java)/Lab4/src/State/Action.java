package State;

import Enums.Actions;
import Exceptions.CanNotCaressException;
import Launch.Main;

public interface Action {
    void doAction(Actions what);
    void doDirectedAction(Actions what, Main.Person whom) throws CanNotCaressException;
}
