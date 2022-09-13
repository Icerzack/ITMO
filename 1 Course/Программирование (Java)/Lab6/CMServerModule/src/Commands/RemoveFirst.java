package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Controller.ServerSide;

import java.util.NoSuchElementException;

public class RemoveFirst extends AbstractCommand implements OutputSetup {
    public RemoveFirst(CollectionManager manager) {
        super(manager);
        setDescription("удалить первый элемент из коллекции");

    }

    @Override
    public synchronized String execute() {
        if (getManager().getPeople().size() != 0) {
            try {
                if(getManager().getPeople().peek().getOwner().equals(ServerSide.activeUsers.get(ServerSide.incoming+"")))
                getManager().getPeople().remove();
                else return ("Вы не являетесь создателем этого объекта - удаление невозможно.");
                return ("Первый элемент коллекции удалён.");
            }
            catch (NoSuchElementException ex) {
                return ("Нельзя удалить первый элемент коллекции. Коллекция пуста.");
            }
        }
        return ("Коллекция пуста.");
    }


    @Override
    public void printInformation(String info) {

    }
}
