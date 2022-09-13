package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Model.Person;

import java.util.Iterator;

public class FilterLessThanPassportId extends AbstractCommand implements OutputSetup {

    String total = "";

    public FilterLessThanPassportId(CollectionManager manager) {
        super(manager);
        setDescription("вывести элементы, значение поля passportID которых меньше заданного.");

    }

    @Override
    public synchronized String execute(String passportID) {
        Iterator<Person> iter = getManager().getPeople().iterator();
        Person person;
        if(!getManager().getPeople().isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                else if(Long.parseLong(person.getPassportID())<Long.parseLong(passportID)){
                    printInformation("Обработка Person: passportID < "+passportID+"=" + person.getPassportID());
                    printInformation(person.getName());
                    printInformation(person.getCoordinates());
                    printInformation(person.getCreationDate()+"");
                    printInformation(person.getHeight()+"");
                    printInformation(person.getPassportID());
                    printInformation(person.getHairColor()+"");
                    printInformation(person.getNationality()+"");
                    printInformation(person.getLocation());
                    printInformation("");
                }
            }
            return total;
        }
        else {return "Коллекция пуста.";}
    }

    @Override
    public void printInformation(String info) {
        total = total.concat(info+"\n");
    }

}
