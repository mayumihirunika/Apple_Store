package Controller;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseController {
    private final String DB_URL = "jdbc:mysql://localhost:3306/apple_store";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "";
    
    private Connection connection;
    private static DatabaseController instance;
    
    public DatabaseController(){
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            System.out.println("Connected");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
