package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Controller.ServerSide;
import Model.Person;

import java.util.Iterator;
import java.util.PriorityQueue;

public class RemoveById extends AbstractCommand implements OutputSetup {


    public RemoveById(CollectionManager manager) {
        super(manager);
        setDescription("удалить элемент из коллекции по его id");

    }

    @Override
    public synchronized String execute(String id) {

        if (getManager().getPeople().size() != 0) {
            try {
//                Iterator<Person> iter = getManager().getPeople().iterator();
//                Person personToDelete = null;
//                while (iter.hasNext()) {
//                    personToDelete = iter.next();
//                    if (personToDelete.getId() == Integer.parseInt(id)) {
//                        break;
//                    }
//                }
                Person personToDelete = getManager().getById(Integer.parseInt(id));
                if (personToDelete == null) {
                    return "Человек с id " + id + " не существует";
                }
                if(!personToDelete.getOwner().equals(ServerSide.activeUsers.get(ServerSide.incoming+""))){
                    return ("Вы не являетесь создателем этого объекта - удаление невозможно");
                }
                PriorityQueue<Person> updatedPriorityQueue = new PriorityQueue<Person>();
                for (Person p : getManager().getPeople()) {
                    if (p.getId() != Integer.parseInt(id)) {
                        updatedPriorityQueue.add(getManager().getPeople().poll());
                    }
                }
                getManager().setNewPeople(updatedPriorityQueue);
                return "Человек с id: "+id+" удален из списка.";
            }catch (Exception e){
                return ("Возникла непредвиденная ошибка...");
            }
        }
        return ("Коллекция пуста.");
    }

    @Override
    public void printInformation(String info) {
        ServerSide.sendMessage(info);
    }
}
