package Controller;

import Commands.*;
import DataBaseUtility.UserHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class ServerSide implements Runnable{

    private final CollectionManager serverCollection;
    public static Socket incoming=null;
    public static HashMap<String, AbstractCommand> availableCommands;

    public static ObjectOutputStream sendToClient;
    public static ObjectInputStream getFromClient;

    private int historyNum = 13;
    private int historyCounter = historyNum-1;
    private final String[] history = new String[historyNum];

    public static HashMap<String,String> activeUsers = new HashMap<>();

    /**
     * @param serverCollection обеспечивает доступ к коллекции.
     * @param incoming активное соединение с клиентской программой.
     * Команды, доступные клиенту, являются объектами {@link AbstractCommand}, хранящимися в
     * {@code HashMap <String, AbstractCommand> availableCommands}.
     */
    ServerSide(CollectionManager serverCollection, Socket incoming) {
        this.serverCollection = serverCollection;
        this.incoming = incoming;
        availableCommands = new HashMap<>();
        availableCommands.put("show", new Show(serverCollection));
        availableCommands.put("remove_first", new RemoveFirst(serverCollection));
        availableCommands.put("info", new Info(serverCollection));
        availableCommands.put("clear", new Clear(serverCollection));
        availableCommands.put("save", new Save(serverCollection));
        availableCommands.put("add", new Add(serverCollection));
        availableCommands.put("remove_by_id", new RemoveById(serverCollection));
        availableCommands.put("add_if_min", new AddIfMin(serverCollection));
        availableCommands.put("filter_by_location", new FilterByLocation(serverCollection));
        availableCommands.put("filter_contains_name", new FilterContainsName(serverCollection));
        availableCommands.put("filter_less_than_passport_i_d", new FilterLessThanPassportId(serverCollection));
        availableCommands.put("update", new Update(serverCollection));
        availableCommands.put("history", new History(serverCollection));
        availableCommands.put("execute_script", new ExecuteScript(serverCollection));
        availableCommands.put("help", new Help(serverCollection, availableCommands));
    }

    /**
     * Запускает активное соединение с клиентом в новом {@link Thread}.
     */
    @Override
    public void run() {
        try (ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream());
             ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream())) {
            ServerSide.sendToClient = sendToClient;
            ServerSide.getFromClient = getFromClient;
            authUser();
            sendToClient.writeObject("Соединение установлено.\nВы можете вводить команды. Ваш поток: "+Thread.currentThread().getId());
            AbstractCommand errorCommand = new AbstractCommand(null) {
                @Override
                public String execute() {
                    return "Неизвестная команда. Введите 'help' для получения списка команд.";
                }
            };
            while (true) {
                try {
                    String requestFromClient = (String) getFromClient.readObject();
                    System.out.println("Получено [" + requestFromClient + "] от " + incoming + ". ");
                    String[] parsedCommand = requestFromClient.trim().split(" ",2);
                    if (parsedCommand.length == 1) {
                        sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute());
                        serverCollection.addToHistory(parsedCommand[0]);
                    }
                    else if (parsedCommand.length == 2) {
                        sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute(parsedCommand[1]));
                        serverCollection.addToHistory(parsedCommand[0]);
                    }
                    System.out.println("Ответ успешно отправлен.");
                } catch (SocketException e) {
                    System.out.println(incoming + " отключился от сервера."); //Windows
                    activeUsers.remove(incoming+"");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(incoming + " отключился от сервера."); //Unix
        }
    }

    public static void sendMessage(String message){
        try {
            sendToClient.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMessage(){
        try {
            return getFromClient.readObject().toString();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Пользователь прервал выполнение команды.");
        }
        return "";
    }

    @Override
    public String toString() {
        return "ServerSide{" +
                "serverSide=" + serverCollection +
                ", incoming=" + incoming +
                ", availableCommands=" + availableCommands +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerSide)) return false;
        ServerSide that = (ServerSide) o;
        return Objects.equals(serverCollection, that.serverCollection) &&
                Objects.equals(availableCommands, that.availableCommands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverCollection, availableCommands);
    }

    public void authUser(){
        try {
            sendToClient.writeObject("Для подключения к БД, введите действущий логин и пароль:" +
                    "\nlogin <user> <password>"+
                    "\nЕсли у вас нет учетной записи - введите следующий синтаксис:" +
                    "\nregister <user> <password>");
            while (true){
                String requestFromClient = (String) getFromClient.readObject();
                System.out.println("Получено [" + requestFromClient + "] от " + incoming + ". ");
                String[] parsedCommand = requestFromClient.trim().split(" ",3);
                if (parsedCommand.length == 3) {
                    UserHandler userHandler = new UserHandler();
                    if(parsedCommand[0].equals("register")){
                        boolean isUserExists = userHandler.userExist(parsedCommand[1]);
                        if(isUserExists){
                            sendToClient.writeObject("Пользователь с данным именем уже существует - придумайте другое.");
                        }
                        else {
                            userHandler.addUser(parsedCommand[1],parsedCommand[2]);
                            activeUsers.put(incoming+"",parsedCommand[1]);
                            System.out.println("Пользователь "+parsedCommand[1]+" зарегестрировался.");
                            System.out.println("Пользователи на сервере: "+activeUsers);
                            break;
                        }
                    }
                    else if(parsedCommand[0].equals("login")){
                        boolean isAccountExists = userHandler.accountExist(parsedCommand[1],parsedCommand[2]);
                        if(isAccountExists){
                            activeUsers.put(incoming+"",parsedCommand[1]);
                            System.out.println("Пользователь "+parsedCommand[1]+" зашел на сервер.");
                            System.out.println("Пользователи на сервере: "+activeUsers);
                            break;
                        }
                        else {
                            sendToClient.writeObject("Неверный логин/пароль.");
                        }
                    }
                    else{
                        sendToClient.writeObject("Введите верный синтаксис!");
                    }
                }
                else {
                    sendToClient.writeObject("Введите верный синтаксис!");
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
