package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Model.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Clear extends AbstractCommand {
    public Clear(CollectionManager manager) {
        super(manager);
        setDescription("Очистить коллекцию.");
    }

    @Override
    public synchronized String execute() {
        getManager().getPeople().clear();
        return "Коллекция очищена.";
    }
}
