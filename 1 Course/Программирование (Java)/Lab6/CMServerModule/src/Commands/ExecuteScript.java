package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;

import java.util.HashMap;
import java.util.TreeMap;

public class ExecuteScript extends AbstractCommand implements OutputSetup {

    String total = "";

    public ExecuteScript(CollectionManager manager) {
        super(manager);
        setDescription("Вызывает скрипт");
    }

    @Override
    public synchronized String execute(String arg) {
        getManager().execute_script(arg);
        return "Скрипт завершен";
    }

    @Override
    public void printInformation(String info) {
        total = total.concat(info+"\n");
    }
}