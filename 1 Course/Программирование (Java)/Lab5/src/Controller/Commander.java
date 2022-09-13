package Controller;
/**
 * Класс управления командами.
 * @autor Максим Кузнецов
 * @version 1.0
 */
import java.io.*;
import java.util.*;

public class Commander implements OutputSetup {

    private CollectionManager manager;
    private String userCommand;
    private String[] finalUserCommand;
    private final int historyNum = 13;
    private int historyCounter = historyNum-1;
    private final String[] history = new String[historyNum];
    {
        userCommand = "";
    }
    /**
     * addToHistory - метод создания "истории" команд
     * @param commandName - имя команды, которую хотим добавить в историю
     */
    private void addToHistory(String commandName){
        if(historyCounter==-1){
            for(int i=historyNum-1;i>0;i--)
            {
                history[i]=history[i-1];
            }
            history[0]=commandName;
        }
        else{
            history[historyCounter]=commandName;
            historyCounter--;
        }
    }
    /**
     * Конструктор - создание нового "каммандера"
     * @param manager - экземпляр "CollectionManager'а", с которым мы работаем
     * @see CollectionManager#CollectionManager(Queue) ()
     */
    public Commander(CollectionManager manager) {
        this.manager = manager;
    }
    /**
     * interactiveMod() - запуск интерактивного режима для работы с коллекциями"
     * @see CollectionManager#CollectionManager(Queue) ()
     */

    public void interactiveMode() throws IOException {
        try(Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                finalUserCommand = userCommand.trim().split(" ", 2);
                try {
                    switch (finalUserCommand[0]) {
                        case "": break;
                        case "remove_first":
                            manager.remove_first();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "add":
                            manager.add();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "save":
                            manager.save();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "show":
                            manager.show();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "clear":
                            manager.clear();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "info":
                            manager.info();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "update":
                            manager.update(Integer.parseInt(finalUserCommand[1]));
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "history":
                            printInformation("Последние "+historyNum+" команд:");
                            for (int i = history.length-1; i >= 0; i--) {
                                if(history[i]==null){
                                    continue;
                                }
                                printInformation(history[i]);
                            }
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "remove_by_id":
                            manager.remove_by_id(Integer.parseInt(finalUserCommand[1]));
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "add_if_min":
                            manager.add_if_min();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "help":
                            manager.help();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "exit":
                            manager.save();
                            break;
                        case "execute_script":
                            execute_script(finalUserCommand[1]);
                            break;
                        case "filter_by_location":
                            manager.filter_by_location(finalUserCommand[1]);
                            break;
                        case "filter_contains_name":
                            manager.filter_contains_name(finalUserCommand[1]);
                            break;
                        case "filter_less_than_passport_i_d":
                            manager.filter_less_than_passport_i_d(finalUserCommand[1]);
                            break;
                        default:
                            printInformation("Неопознанная команда. Наберите 'help' для справки.");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    printInformation("Отсутствует аргумент.");
                }
            }
        }
    }
    /**
     * execute_script() - метод для обработки команд из скрипта"
     * @see CollectionManager#CollectionManager(Queue) ()
     */
    private void execute_script(String file_name){
        File file = new File(file_name);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()||!userCommand.equals("execute_script")){
                userCommand = scanner.nextLine();
                finalUserCommand = userCommand.trim().split(" ", 2);
                try {
                    switch (finalUserCommand[0]) {
                        case "": break;
                        case "remove_first":
                            manager.remove_first();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "add":
                            manager.add();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "save":
                            manager.save();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "show":
                            manager.show();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "clear":
                            manager.clear();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "info":
                            manager.info();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "update":
                            manager.update(Integer.parseInt(finalUserCommand[1]));
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "history":
                            printInformation("Последние "+historyNum+" команд:");
                            for (int i = history.length-1; i >= 0; i--) {
                                if(history[i]==null){
                                    continue;
                                }
                                printInformation(history[i]);
                            }
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "remove_by_id":
                            manager.remove_by_id(Integer.parseInt(finalUserCommand[1]));
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "add_if_min":
                            manager.add_if_min();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "help":
                            manager.help();
                            addToHistory(finalUserCommand[0]);
                            break;
                        case "exit":
                            manager.save();
                            break;
                        case "execute_script":
                            printInformation("Обнаружена команда execute_script - выполнение скрипта приостановлено");
                            break;
                        case "filter_by_location":
                            manager.filter_by_location(finalUserCommand[1]);
                            break;
                        case "filter_contains_name":
                            manager.filter_contains_name(finalUserCommand[1]);
                            break;
                        case "filter_less_than_passport_i_d":
                            manager.filter_less_than_passport_i_d(finalUserCommand[1]);
                            break;
                        default:
                            printInformation("Неопознанная команда. Наберите 'help' для справки.");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    printInformation("Отсутствует аргумент.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commander)) return false;
        Commander commander = (Commander) o;
        return Objects.equals(manager, commander.manager);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(manager, userCommand);
        result = 31 * result + Arrays.hashCode(finalUserCommand);
        return result;
    }

    @Override
    public void printInformation(String info) {
        System.out.println(info);
    }
}