package Model;
/**
 * Класс для хранения данных о локации
 * @autor Максим Кузнецов
 * @version 1.0
 */
public class Location {
    private Double x; //Поле не может быть null
    private double y;
    private String name; //Строка не может быть пустой, Поле не может быть null

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}