package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;
import Model.Person;

import java.util.Iterator;

public class FilterContainsName extends AbstractCommand implements OutputSetup {

    String total = "";

    public FilterContainsName(CollectionManager manager) {
        super(manager);
        setDescription("ввывести элементы, значение поля name которых содержит заданную подстроку.");

    }
    @Override
    public synchronized String execute(String name) {
        Iterator<Person> iter = getManager().getPeople().iterator();
        Person person;
        if(!getManager().getPeople().isEmpty()){
            while (iter.hasNext()) {
                person = iter.next();
                if(person == null) break;
                else if(person.getName().contains(name)){
                    printInformation("Обработка Person: name=" + person.getName());
                    printInformation(person.getId()+"");
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
