package Model;
/**
 * Класс для хранения данных о координатах
 * @autor Максим Кузнецов
 * @version 1.0
 */
public class Coordinates {
    private Double x; //Максимальное значение поля: 203, Поле не может быть null
    private long y;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}