package DataBaseUtility;

import java.sql.*;

public class PeopleDataBase {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/students";
    static final String USER = "s311694";
    static final String PASS = "zhm878";
    static final String QUERY_FOR_ALL_PEOPLE = "SELECT id, name, coordinates, height, passportid, haircolor, nationality, location, owner FROM people";
    static final String QUERY_GET_BY_ID = "SELECT id, name, coordinates, height, passportid, haircolor, nationality, location, owner FROM people WHERE id=";


    public void printPeople()  {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY_FOR_ALL_PEOPLE);
        ) {
            while(rs.next()){
                //Display values
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Coordinates: " + rs.getString("coordinates"));
                System.out.println("Height: " + rs.getString("height"));
                System.out.println("PassportID: " + rs.getString("passportid"));
                System.out.println("Hair Color: " + rs.getString("haircolor"));
                System.out.println("Nationality: " + rs.getString("nationality"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Owner: " + rs.getString("owner"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getPersonById(int id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY_GET_BY_ID+id);
        ) {
            while(rs.next()){
                //Display values
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Coordinates: " + rs.getString("coordinates"));
                System.out.println("Height: " + rs.getString("height"));
                System.out.println("PassportID: " + rs.getString("passportid"));
                System.out.println("Hair Color: " + rs.getString("haircolor"));
                System.out.println("Nationality: " + rs.getString("nationality"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Owner: " + rs.getString("owner"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
