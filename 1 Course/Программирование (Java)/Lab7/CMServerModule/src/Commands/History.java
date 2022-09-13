package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;

import java.util.HashMap;
import java.util.TreeMap;

public class History extends AbstractCommand implements OutputSetup {

    String total = "";

    public History(CollectionManager manager) {
        super(manager);
        setDescription("Показывает список доступных команд.");
    }

    @Override
    public synchronized String execute() {
        for (int i = getManager().history.length-1; i >= 0; i--) {
            if(getManager().history[i]==null){
                continue;
            }
            printInformation(getManager().history[i]);
        }
        return total;
    }

    @Override
    public void printInformation(String info) {
        total = total.concat(info+"\n");
    }
}