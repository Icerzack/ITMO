package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Controller.ServerSide;
import Model.*;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.FormatterClosedException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Add extends AbstractCommand implements OutputSetup {

    String total = "";

    public Add(CollectionManager manager) {
        super(manager);
        setDescription("Добавить новый элемент в коллекцию.");
    }

    @Override
    public synchronized String execute() {
            String name = null;
            Coordinates coordinates = new Coordinates();
            Color color = null;
            Country country = null;
            Location location = new Location();
            String temp2;
            Double temp1;
            double temp4;
            long temp3 = 0;
            float height = 0;
            Long passportID = null;
            boolean passed = false;
            printInformation("Введите name");
            while (!passed) {
                try {
                    name = getInformation();
                    try {
                        Long.parseLong(name);
                        printInformation("Invalid input! Try again:");
                    }
                    catch (Exception e){
                        if(name==null){

                        }
                        else{
                            passed=true;
                        }
                    }

                }
                catch (FormatterClosedException exp) {
                    printInformation(exp+"");
                    break;
                }
                catch (NoSuchElementException exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите coordinates, сначала x, потом y (1<=х<=203 и y-любое)\nВведите x");
//            printInformation("Введите x");
            while (true) {
                try {
                    temp1 = Double.parseDouble(getInformation());
                    if(temp1<1||temp1>203){
                        printInformation("Invalid input! Try again:");
                    }
                    else{
                        coordinates.setX(temp1);
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите y");
            while (true) {
                try {
                    temp3 = Long.parseLong(getInformation());
                    if(temp3==0){
                        printInformation("Invalid input! Try again:");
                    }
                    else{
                        coordinates.setY(temp3);
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите height");
            while (true) {
                try {
                    height = Float.parseFloat(getInformation());
                    if(height==0){
                        printInformation("Invalid input! Try again:");
                    }
                    else{
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите passportID");
            while (true) {
                try {
                    passportID = Long.parseLong(getInformation());
                    Iterator<Person> iter = getManager().getPeople().iterator();
                    Person testPerson;
                    while (iter.hasNext()) {
                        testPerson = iter.next();
                        if(testPerson.getPassportID().equals(passportID)){
                            printInformation("Человек с таким PassportID уже существует");
                        }
                    }
                    break;
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите hairColor\nВозможные варианты:\n"+java.util.Arrays.asList(Color.values())+"");
//            printInformation("Возможные варианты:");
//            printInformation(java.util.Arrays.asList(Color.values())+"");
            while (true) {
                try {
                    temp2 = getInformation();
                    if(temp2!=null) {
                        switch (temp2) {
                            case "GREEN":
                                color = Color.GREEN;
                                break;
                            case "BLUE":
                                color = Color.BLUE;
                                break;
                            case "ORANGE":
                                color = Color.ORANGE;
                                break;
                            case "WHITE":
                                color = Color.WHITE;
                                break;
                            case "BROWN":
                                color = Color.BROWN;
                                break;
                            default:
                                printInformation("Invalid input! Try again:");
                        }
                    }
                    else{
                    }
                    if(color!=null){
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите nationality \nВозможные варианты: \n"+java.util.Arrays.asList(Country.values())+"");
//            printInformation(java.util.Arrays.asList(Country.values())+"");
            while (true) {
                try {
                    temp2 = getInformation();
                    if(temp2!=null) {
                        switch (temp2) {
                            case "UNITED_KINGDOM":
                                country = Country.UNITED_KINGDOM;
                                break;
                            case "USA":
                                country = Country.USA;
                                break;
                            case "FRANCE":
                                country = Country.FRANCE;
                                break;
                            case "VATICAN":
                                country = Country.VATICAN;
                                break;
                            case "JAPAN":
                                country = Country.JAPAN;
                                break;
                            default:
                                printInformation("Invalid input! Try again:");
                        }
                    }
                    else{

                    }
                    if(country!=null){
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите location, сначала x, потом y, потом name (х!=0 и y-любое, name-непустое)\nВведите x");
//            printInformation("Введите x");
            while (true) {
                try {
                    temp1 = Double.parseDouble(getInformation());
                    if(temp1==0){
                        printInformation("Invalid input! Try again:");
                    }
                    else{
                        location.setX(temp1);
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите y");
            while (true) {
                try {
                    temp4 = Double.parseDouble(getInformation());
                    if(temp4==0){
                        printInformation("Invalid input! Try again:");
                    }
                    else{
                        location.setY(temp3);
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            printInformation("Введите name");
            while (true) {
                try {
                    temp2 = getInformation();
                    if(temp2==null){
                        printInformation("Invalid input! Try again:");
                    }
                    else{
                        location.setName(temp2);
                        break;
                    }
                }
                catch (Exception exp) {
                    printInformation("Invalid input! Try again:");
                }
            }
            CollectionManager.finalId +=1;
            int id = CollectionManager.finalId;
            ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
            try {
                getManager().addPersonToDataBase(name,coordinates,zonedDateTimeNow,height,passportID+"",color,country,location,ServerSide.activeUsers.get(ServerSide.incoming+""));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return "Произошла ошибка добавления человека в базу данных.";
            }
            getManager().getPeople().add(new Person(id,name,coordinates,zonedDateTimeNow,height,passportID+"",color,country,location,ServerSide.activeUsers.get(ServerSide.incoming+"")));
            return "Элемент успешно добавлен.";
    }

    @Override
    public void printInformation(String info) {
        ServerSide.sendMessage(info);
    }
    public String getInformation(){
        return ServerSide.getMessage();
    }
}
