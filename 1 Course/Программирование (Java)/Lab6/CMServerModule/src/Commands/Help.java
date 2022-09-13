package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;

import java.util.HashMap;
import java.util.TreeMap;

public class Help extends AbstractCommand implements OutputSetup {
    protected static TreeMap<String, String> manual;

    String total = "";

    static {
        manual = new TreeMap<>();
        manual.put("help", "вывести справку по доступным командам");
        manual.put("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        manual.put("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        manual.put("add {element}", "добавить новый элемент в коллекцию");
        manual.put("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        manual.put("remove_by_id id", "удалить элемент из коллекции по его id");
        manual.put("clear", "очистить коллекцию");
        manual.put("save", "сохранить коллекцию в файл");
        manual.put("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        manual.put("exit", "завершить программу (без сохранения в файл)");
        manual.put("remove_first", "удалить первый элемент из коллекции");
        manual.put("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        manual.put("history", "вывести последние 13 команд (без их аргументов)");
        manual.put("filter_by_location location", "вывести элементы, значение поля location которых равно заданному");
        manual.put("filter_contains_name name", "вывести элементы, значение поля name которых содержит заданную подстроку");
        manual.put("filter_less_than_passport_i_d passportID", "вывести элементы, значение поля passportID которых меньше заданного");
    }

    public Help(CollectionManager manager, HashMap<String, AbstractCommand> commands) {
        super(manager);
        setDescription("Показывает список доступных команд.");
    }

    @Override
    public synchronized String execute() {
        for (String s:manual.keySet()) {
            printInformation(s+" -- "+manual.get(s));
        }
        return total;
    }

    @Override
    public void printInformation(String info) {
        total = total.concat(info+"\n");
    }
}
