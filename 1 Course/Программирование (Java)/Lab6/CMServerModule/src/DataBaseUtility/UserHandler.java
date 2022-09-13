package DataBaseUtility;

import java.sql.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserHandler {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/students";
    static final String USER = "s311694";
    static final String PASS = "zhm878";

    public void loginUser(String name, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL,USER, PASS)) {
            try (PreparedStatement ps = connection.prepareStatement("GET  users (name,password) values (?,?)")) {
                ps.setObject(0,Math.random()*10000);
                ps.setObject(1,name);
                ps.setObject(2,password);
                ps.executeUpdate();
            }
        }
    }

    public void addUser(String name,String password) throws SQLException{
        try (Connection connection = DriverManager.getConnection(DB_URL,USER, PASS)) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO users (name,password,MD2) values (?,?,?)")) {
                ps.setObject(1,name);
                ps.setObject(2,password);
                ps.setObject(3, MD2(password));
                ps.executeUpdate();
            }
        }

    }

    public boolean userExist(String username) throws SQLException{
        boolean exist = false;
        try (Connection connection = DriverManager.getConnection(DB_URL,USER, PASS)) {
            try (Statement ps = connection.createStatement()) {
                try (ResultSet rs = ps.executeQuery("SELECT name,password FROM users")) {
                    while(rs.next()) {
                        String name = rs.getString("name");
                        if (name.equals(username)) {
                            exist = true;
                            break;
                        }
                    }
                }
            }
        }
        return exist;
    }


    public boolean accountExist(String username,String password) throws SQLException{
        boolean exist = false;
        try (Connection connection = DriverManager.getConnection(DB_URL,USER, PASS)) {
            try (Statement ps = connection.createStatement()) {
                try (ResultSet rs = ps.executeQuery("SELECT name,password FROM users")) {
                    while(rs.next()) {
                        String name = rs.getString("name");
                        String pass = rs.getString("password");
                        if (name.equals(username)&&password.equals(pass)) {
                            exist = true;
                        }
                    }
                }
            }
        }
        return exist;
    }

    public static String MD2(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
