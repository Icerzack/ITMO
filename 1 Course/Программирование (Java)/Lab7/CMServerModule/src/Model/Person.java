package Model;

import DataBaseUtility.User;

import java.time.ZonedDateTime;
/**
 * Класс для хранения данных о людях
 * @autor Максим Кузнецов
 * @version 1.0
 */
public class Person implements Comparable<Person>{
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float height; //Значение поля должно быть больше 0
    private String passportID; //Значение этого поля должно быть уникальным, Поле не может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле не может быть null
    private String owner;
    /**
     * Конструктор - создает человека с переданными ему параметрами.
     */
    public Person(Integer id, String name, Coordinates coordinates, ZonedDateTime creationDate, float height, String passportID, Color hairColor, Country nationality, Location location, String owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates.getX()+";"+coordinates.getY();
    }

    public void setCoordinates(Double x, long y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public String getLocation() {
        return location.getX()+";"+location.getY()+";"+location.getName();
    }

    public void setLocation(Double x, double y, String name) {
        this.location.setX(x);
        this.location.setY(y);
        this.location.setName(name);
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }
}