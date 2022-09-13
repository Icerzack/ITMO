package Commands;

import Controller.CollectionManager;
import Controller.OutputSetup;

public class Save extends AbstractCommand implements OutputSetup {

    public Save(CollectionManager manager) {
        super(manager);
        setDescription("сохранить коллекцию в файл");

    }

    @Override
    public synchronized String execute() {
        try {
            getManager().save();
            return "Изменения сохранены.";
        } catch (Exception ex) {
            return "Произошло непредвиденная ошибка на сервере. Коллекция не может быть сохранена. Попробуйте ещё раз позже.";
        }
    }

    @Override
    public void printInformation(String info) {

    }
}
