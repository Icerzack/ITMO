package Controller;

import DataBaseUtility.UserHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

public class ServerConnection {

    private static ArrayList<Object> clientOutputStreams;

    private static CollectionManager serverCollection;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8888)) {
            System.out.println("Сервер начал слушать клиентов. " + "\nПорт: " + server.getLocalPort() +
                    " / Адрес: " + InetAddress.getLocalHost() + ".\nОжидаем подключения клиентов. ");
            Thread pointer = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print(".");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.print("\n");
                        Thread.currentThread().interrupt();
                    }
                }
            });

//            Map hashMap = new HashMap();
//            hashMap.put("Collman_Path","file.xml");
//            try {
//                setEnv((Map<String, String>) hashMap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            serverCollection = new CollectionManager(System.getenv("Collman_Path"));
            clientOutputStreams = new ArrayList<>();
            pointer.setDaemon(true);
            pointer.start();
            ForkJoinPool pool = new ForkJoinPool();
            while (true) {
                Socket incoming = server.accept();
                pointer.interrupt();
                System.out.println(incoming + " подключился к серверу.");
                ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream());
                clientOutputStreams.add(sendToClient);
                ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream());
                ServerSide r = new ServerSide(serverCollection, incoming, sendToClient, getFromClient);
                Thread t = new Thread(r);
                pool.execute(t);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    static public void tellEveryone(String message){
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()){
            try{
                ObjectOutputStream writer = (ObjectOutputStream) it.next();
                writer.writeObject(message);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static public void tellSpecific(String message, ObjectOutputStream whom){
        try {
            whom.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void authUser(ObjectOutputStream sendToClient, ObjectInputStream getFromClient, Socket incoming){
        try {
            tellSpecific("Для подключения к БД, введите действущий логин и пароль:" +
                    "\nlogin <user> <password>"+
                    "\nЕсли у вас нет учетной записи - введите следующий синтаксис:" +
                    "\nregister <user> <password>",sendToClient);
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
                            System.out.println("Пользователь "+parsedCommand[1]+" зарегистрировался.");
                            break;
                        }
                    }
                    else if(parsedCommand[0].equals("login")){
                        boolean isAccountExists = userHandler.accountExist(parsedCommand[1],parsedCommand[2]);
                        if(isAccountExists){
                            System.out.println("Пользователь "+parsedCommand[1]+" зашел на сервер.");
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
            try {
                sendToClient.writeObject("БД сейчас недоступна - повторите позднее.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    // Временный метод, чтобы ускорить процесс создания переменной окружения
    protected static void setEnv(Map<String, String> newenv) throws Exception {
        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        } catch (NoSuchFieldException e) {
            Class[] classes = Collections.class.getDeclaredClasses();
            Map<String, String> env = System.getenv();
            for(Class cl : classes) {
                if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                    Field field = cl.getDeclaredField("m");
                    field.setAccessible(true);
                    Object obj = field.get(env);
                    Map<String, String> map = (Map<String, String>) obj;
                    map.clear();
                    map.putAll(newenv);
                }
            }
        }
    }

}
