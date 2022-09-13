package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;

public class Show extends AbstractCommand implements OutputSetup {

    String total = "";

    public Show(CollectionManager manager) {
        super(manager);
        setDescription("вывести в стандартный поток вывода все элементы коллекции в строковом представлении");

    }

    @Override
    public synchronized String execute() {
        if(!getManager().getPeople().isEmpty()){
            total = getManager().printPeopleFromDataBase();
            return total;
        }
        else {return ("Коллекция пуста.");}
    }

    @Override
    public void printInformation(String info) {
        total = total.concat(info+"\n");
    }
}
