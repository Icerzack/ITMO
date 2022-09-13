package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Controller.ServerSide;
import Model.Color;
import Model.Country;
import Model.Person;

import java.util.FormatterClosedException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Update extends AbstractCommand implements OutputSetup {

    public Update(CollectionManager manager) {
        super(manager);
        setDescription("обновить значение элемента коллекции, id которого равен заданному");

    }

    @Override
    public synchronized String execute(String id) {
        try {
            Scanner scanner = new Scanner(System.in);
            Iterator<Person> iter = getManager().getPeople().iterator();
            Person person = null;
            while (iter.hasNext()) {
                person = iter.next();
                if (person.getId() == Integer.parseInt(id)) {
                    break;
                }
            }
            if (person == null) {
                return ("Человек с id " + id + " не существует");
            }
            if(!person.getOwner().equals(ServerSide.activeUsers.get(ServerSide.incoming+""))){
                return ("Вы не являетесь создателем этого объекта - изменение невозможно");
            }
            String name;
            Color color = null;
            Country country = null;
            String temp1;
            Double xC = null;
            Double xL = null;
            double yL = 0;
            long yC;
            float height;
            String passportID;
            printInformation("Введите name - текущее значение " + person.getName() + " (для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    name = getInformation();
                    if (name == null) {
                        printInformation("Invalid input! Try again:");
                    } else if (name.equals("YES")) {
                        break;
                    } else {
                        try {
                            Long.parseLong(name);
                            printInformation("Invalid input! Try again:");
                        } catch (Exception e) {
                            person.setName(name);
                            getManager().updatePersonDataInDataBase(Integer.parseInt(id),"name",name);
                            break;
                        }
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите coordinates, сначала x, потом y (1<=х<=203 и y-любое)\nВведите x - текущее значение " + person.getCoordinates().split(";")[0] + "(для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1.equals("YES")) {
                        break;
                    } else {
                        try {
                            xC = Double.parseDouble(temp1);
                            if (xC < 1 || xC > 203) {
                                printInformation("Invalid input! Try again:");
                            } else {
                                getManager().updatePersonDataInDataBase(Integer.parseInt(id),"coordinates",xC+";"+Long.parseLong(person.getCoordinates().split(";")[1]));
                                person.setCoordinates(xC, Long.parseLong(person.getCoordinates().split(";")[1]));
                                break;
                            }
                        } catch (Exception e) {
                            printInformation("Invalid input! Try again:");
                        }
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите y - текущее значение " + person.getCoordinates().split(";")[1] + " (для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1.equals("YES")) {
                        break;
                    } else {
                        try {
                            yC = Long.parseLong(temp1);
                            if (yC == 0) {
                                printInformation("Invalid input! Try again:");
                            } else {
                                if (xC != 0) {
                                    getManager().updatePersonDataInDataBase(Integer.parseInt(id),"coordinates",xC+";"+yC);
                                    person.setCoordinates(xC, yC);
                                } else {
                                    getManager().updatePersonDataInDataBase(Integer.parseInt(id),"coordinates",Double.parseDouble(person.getCoordinates().split(";")[0])+";"+yC);
                                    person.setCoordinates(Double.parseDouble(person.getCoordinates().split(";")[0]), yC);
                                }
                                break;
                            }
                        } catch (Exception e) {
                            printInformation("Invalid input! Try again:");
                        }
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите height - текущее значение " + person.getHeight() + " (для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1.equals("YES")) {
                        break;
                    } else {
                        try {
                            height = Float.parseFloat(temp1);
                            if (height <= 0) {
                                printInformation("Invalid input! Try again:");
                            } else {
                                getManager().updatePersonDataInDataBase(Integer.parseInt(id),"height",height+"");
                                person.setHeight(height);
                                break;
                            }
                        } catch (Exception e) {
                            printInformation("Invalid input! Try again:");
                        }
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите passportID - текущее значение " + person.getPassportID() + " (для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    passportID = getInformation();
                    if (passportID == null) {
                        printInformation("Invalid input! Try again:");
                    } else if (passportID.equals("YES")) {
                        break;
                    } else {
                        try {
                            Long.parseLong(passportID);
                            Iterator<Person> iter1 = getManager().getPeople().iterator();
                            Person testPerson;
                            while (iter1.hasNext()) {
                                testPerson = iter1.next();
                                if (testPerson.getPassportID().equals(passportID)) {
                                    printInformation("Человек с таким PassportID уже существует");
                                }
                            }
                            getManager().updatePersonDataInDataBase(Integer.parseInt(id),"passportid",passportID+"");
                            person.setPassportID(passportID);
                            break;
                        } catch (Exception e) {
                            printInformation("Invalid input! Try again:");
                        }
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите hairColor - текущее значение " + person.getHairColor() + " (для сохранения текущего значения, введите YES)" +
                    "\nВозможные варианты:" +
                    "\n"+java.util.Arrays.asList(Color.values()) + "");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1.equals("YES")) {
                        break;
                    } else if (temp1 != null) {
                        switch (temp1) {
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
                    if (color != null) {
                        getManager().updatePersonDataInDataBase(Integer.parseInt(id),"haircolor",color+"");
                        person.setHairColor(color);
                        break;
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите nationality - текущее значение " + person.getNationality() + " (для сохранения текущего значения, введите YES)" +
                    "\nВозможные варианты:" +
                    "\n"+java.util.Arrays.asList(Country.values()) + "");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1.equals("YES")) {
                        break;
                    } else if (temp1 != null) {
                        switch (temp1) {
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
                    if (country != null) {
                        getManager().updatePersonDataInDataBase(Integer.parseInt(id),"nationality",country+"");
                        person.setNationality(country);
                        break;
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите location, сначала x, потом y, потом name (х!=0 и y-любое, name-непустое)\n" +
                    "Введите x - текущее значение " + person.getLocation().split(";")[0] + " (для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1.equals("YES")) {
                        break;
                    } else {
                        try {
                            xL = Double.parseDouble(temp1);
                            if (xL == 0) {
                                printInformation("Invalid input! Try again:");
                            } else {
                                getManager().updatePersonDataInDataBase(Integer.parseInt(id),"location",xL+";"+Double.parseDouble(person.getLocation().split(";")[1])+";"+person.getLocation().split(";")[2]);
                                person.setLocation(xL, Double.parseDouble(person.getLocation().split(";")[1]), person.getLocation().split(";")[2]);
                                break;
                            }
                        } catch (Exception e) {
                            printInformation("Invalid input! Try again:");
                        }
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите y - текущее значение " + person.getLocation().split(";")[1] + " (для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1.equals("YES")) {
                        break;
                    } else {
                        try {
                            yL = Double.parseDouble(temp1);
                            if (xL != 0) {
                                getManager().updatePersonDataInDataBase(Integer.parseInt(id),"location",xL+";"+yL+";"+person.getLocation().split(";")[2]);
                                person.setLocation(xL, yL, person.getLocation().split(";")[2]);
                            } else {
                                getManager().updatePersonDataInDataBase(Integer.parseInt(id),"location",Double.parseDouble(person.getLocation().split(";")[0])+";"+yL+";"+person.getLocation().split(";")[2]);
                                person.setLocation(Double.parseDouble(person.getLocation().split(";")[0]), yL, person.getLocation().split(";")[2]);
                            }
                            break;
                        } catch (Exception e) {
                            printInformation("Invalid input! Try again:");
                        }
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }
            printInformation("Введите name - текущее значение " + person.getLocation().split(";")[2] + " (для сохранения текущего значения, введите YES)");
            while (true) {
                try {
                    temp1 = getInformation();
                    if (temp1 == null) {
                        printInformation("Invalid input! Try again:");
                    } else if (temp1.equals("YES")) {
                        break;
                    } else {
                        try {
                            Long.parseLong(temp1);
                            printInformation("Invalid input! Try again:");
                        } catch (Exception e) {
                            if (xL != 0) {
                                if (yL != 0) {
                                    getManager().updatePersonDataInDataBase(Integer.parseInt(id),"location",xL+";"+yL+";"+temp1);
                                    person.setLocation(xL, yL, temp1);
                                } else {
                                    getManager().updatePersonDataInDataBase(Integer.parseInt(id),"location",xL+";"+Double.parseDouble(person.getLocation().split(";")[1])+";"+temp1);
                                    person.setLocation(xL, Double.parseDouble(person.getLocation().split(";")[1]), temp1);
                                }
                            } else {
                                if (yL != 0) {
                                    getManager().updatePersonDataInDataBase(Integer.parseInt(id),"location",Double.parseDouble(person.getLocation().split(";")[0])+";"+yL+";"+temp1);
                                    person.setLocation(Double.parseDouble(person.getLocation().split(";")[0]), yL, temp1);
                                } else {
                                    getManager().updatePersonDataInDataBase(Integer.parseInt(id),"location",Double.parseDouble(person.getLocation().split(";")[0])+";"+Double.parseDouble(person.getLocation().split(";")[1])+";"+temp1);
                                    person.setLocation(Double.parseDouble(person.getLocation().split(";")[0]), Double.parseDouble(person.getLocation().split(";")[1]), temp1);
                                }
                            }
                            break;
                        }
                    }
                } catch (Exception exp) {
                    printInformation("Возникла ошибка");
                }
            }

            return "Элемент успешно добавлен.";
        }catch (Exception e){
            return "Синтаксическая ошибка. Не удалось обновить элемент.";
        }
    }

    @Override
    public void printInformation(String info) {
        ServerSide.sendMessage(info);
    }
    public String getInformation(){
        return ServerSide.getMessage();
    }
}
