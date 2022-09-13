package Controller;

import Model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
/**
 * Класс извлечение данных для новой коллекции. Отвечает за создание коллекции из исходного документа
 * @autor Максим Кузнецов
 * @version 1.0
 */
public class PriorityQueueCollector implements OutputSetup {

    String pathToFile;
    static int finalId = 0;
    boolean errorTrigger = false;
    Queue<Person> personPriorityQueue;

    static ZonedDateTime zonedDateTimeOfCreation = ZonedDateTime.now(ZoneId.of("UTC"));
    /**
     * Функция, которая позволяет получить экземпляр созданной очереди.
     */
    public Queue<Person> getPersonPriorityQueue() {
        return personPriorityQueue;
    }
    /**
     * Конструктор - создает пустую очередь с компаратором для сравнения(в текущем коде это не используется)
     */
    public PriorityQueueCollector(String path) {
        personPriorityQueue = new PriorityQueue<Person>(idComparator);
        this.pathToFile = path;
    }
    /**
     * Comparator - компаратор для сравнения(в текущем коде это не используется)
     */
    private Comparator<Person> idComparator = new Comparator<Person>(){

        @Override
        public int compare(Person c1, Person c2) {
            return c1.getId() - c2.getId();
        }
    };
    /**
     * addDataToQueue - создает пустую очередь, и добавляет в нее данные из файла.
     */
    public void addDataToQueue() {
        parseXML(pathToFile, personPriorityQueue);
    }
    /**
     * parseXML - функция для парсинга XML.
     */
    private void parseXML(String pathToFile, Queue<Person> personPriorityQueue) {
        try {

            HashMap hashMap = new HashMap();

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream( pathToFile ), "UTF-8");
            BufferedReader reader = new BufferedReader( inputStreamReader );
            InputSource inputSource = new InputSource( reader );
            Document document = documentBuilder.parse(inputSource);

            Node root = document.getDocumentElement();

            NodeList books = root.getChildNodes();
            for (int i = 0; i < books.getLength(); i++) {
                Node book = books.item(i);
                if (book.getNodeType() != Node.TEXT_NODE) {
                    Coordinates coordinates = null;
                    Color color = null;
                    Country country = null;
                    Location location = null;
                    NodeList bookProps = book.getChildNodes();
                    for(int j = 0; j < bookProps.getLength(); j++) {
                        Node bookProp = bookProps.item(j);
                        if (bookProp.getNodeType() != Node.TEXT_NODE) {
                            if((bookProp.getNodeName()+"").equals("coordinates")){
                                String string = bookProp.getChildNodes().item(0).getTextContent()+"";
                                String[] parts = string.split(";");
                                String part1 = parts[0];
                                String part2 = parts[1];
                                coordinates = new Coordinates();
                                try {
                                    coordinates.setX(Double.parseDouble(part1));
                                    coordinates.setY(Long.parseLong(part2));
                                }catch (Exception e){
                                    printInformation("Поле coordinates у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                    errorTrigger = true;
                                }
                            }
                            else if((bookProp.getNodeName()+"").equals("hairColor")){
                                switch (bookProp.getChildNodes().item(0).getTextContent()+"") {
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
                                        printInformation("Поле hairColor у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                        errorTrigger = true;
                                }
                            }
                            else if((bookProp.getNodeName()+"").equals("nationality")){
                                switch (bookProp.getChildNodes().item(0).getTextContent()+"") {
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
                                        printInformation("Поле nationality у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                        errorTrigger = true;
                                }
                            }
                            else if((bookProp.getNodeName()+"").equals("location")){
                                String string = bookProp.getChildNodes().item(0).getTextContent()+"";
                                String[] parts = string.split(";");
                                String part1 = parts[0];
                                String part2 = parts[1];
                                String part3 = parts[2];
                                location = new Location();
                                try {
                                    location.setX(Double.parseDouble(part1));
                                    location.setY(Long.parseLong(part2));
                                    location.setName(part3);
                                }catch (Exception e){
                                    printInformation("Поле location у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                    errorTrigger = true;
                                }
                            }
                            else {
                                if((bookProp.getNodeName()+"").equals("height")){
                                    String string = bookProp.getChildNodes().item(0).getTextContent()+"";
                                    try {
                                        float f = Float.parseFloat(string);
                                        hashMap.put("height",f);
                                    }catch (Exception e){
                                        printInformation("Поле height у объекта №"+(i-i/2)+" содержит неверное значение - необходимо исправить");
                                        errorTrigger = true;
                                    }
                                }
                                hashMap.put(bookProp.getNodeName()+"",bookProp.getChildNodes().item(0).getTextContent()+"");
                            }
                        }
                    }
                    if(!errorTrigger){
                        finalId+=1;
                        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
                        personPriorityQueue.add(new Person(finalId,hashMap.get("name")+"",coordinates,zonedDateTimeNow,Float.parseFloat(hashMap.get("height")+""),hashMap.get("passportId")+"",color,country,location));
                    }
                }
            }

        } catch (Exception ex) {
            printInformation("Файл с таким именем не найден/Ошибка в файле, перепроверьте синтаксис");
        }
    }

    @Override
    public void printInformation(String info) {
        System.out.println(info);
    }
}