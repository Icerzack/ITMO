package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;

public class Info extends AbstractCommand implements OutputSetup {

    String total = "";

    public Info(CollectionManager manager) {
        super(manager);
        setDescription("вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public synchronized String execute() {
        printInformation("Тип коллекции: "+getManager().getPeople().getClass());
        printInformation("Размер коллекции: "+getManager().getPeople().size());
        printInformation("Время создания коллекции: "+CollectionManager.zonedDateTimeOfCreation);
        return total;
    }

    @Override
    public void printInformation(String info) {
        total = total.concat(info+"\n");
    }
}
