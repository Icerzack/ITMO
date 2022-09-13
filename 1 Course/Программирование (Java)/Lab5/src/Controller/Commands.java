package Controller;

public interface Commands {
    void remove_first();
    /**
     * Функция добавления новго элемента в очередь {@link CollectionManager#add()} ()}
     */
    void add();
    /**
     * Функция полной очистки очереди {@link CollectionManager#clear()} ()}
     */
    void clear();
    /**
     * Функция вывода доступных команд {@link CollectionManager#help()} ()}
     */
    void help();
    /**
     * Функция сохранения очереди {@link CollectionManager#save()} ()}
     */
    void save();
    /**
     * Функция отображения информации об очереди {@link CollectionManager#info()} ()}
     */
    void info();
    /**
     * Функция показа элементов очереди {@link CollectionManager#show()} ()}
     */
    void show();
    /**
     * Функция обновления элемента очереди {@link CollectionManager#update(int)} ()}
     */
    void update(int id);
    /**
     * Функция удаления элемента очереди по id{@link CollectionManager#remove_by_id(int)} ()}
     */
    void remove_by_id(int id);
    /**
     * Функция добавленмя элемента в очередь, с проверкой условия на name{@link CollectionManager#add_if_min()} ()}
     */
    void add_if_min();
    /**
     * Функция вывода элементов очереди по их Location{@link CollectionManager#filter_by_location(String)} ()}
     */
    void filter_by_location(String location);
    /**
     * Функция вывода элементов очереди по их Name {@link CollectionManager#filter_contains_name(String)} ()}
     */
    void filter_contains_name(String name);
    /**
     * Функция вывода элементов очереди по их Location, который меньше заданного значения {@link CollectionManager#filter_less_than_passport_i_d(String)} ()}
     */
    void filter_less_than_passport_i_d(String passportID);
}
