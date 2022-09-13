package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Model.Person;

import java.util.Iterator;

public class FilterByLocation extends AbstractCommand implements OutputSetup {

    String total = "";

    public FilterByLocation(CollectionManager manager) {
        super(manager);
        setDescription("вывести элементы, значение поля location которых равно заданному.");
    }
    @Override
    public synchronized String execute(String location) {
        Iterator<Person> iter = getManager().getPeople().iterator();
        Person person;
        if(!getManager().getPeople().isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                else if(person.getLocation().equals(location)){
                    printInformation("Обработка Person: location=" + person.getLocation());
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
