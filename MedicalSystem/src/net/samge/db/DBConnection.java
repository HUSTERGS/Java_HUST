package net.samge.db;
import java.sql.*;
import org.mariadb.jdbc.Driver;


public class DBConnection{
    private Connection conn;
    private static DBConnection dbConnection;

    private DBConnection()throws ClassNotFoundException,SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        conn=DriverManager.getConnection("jdbc:mysql://localhost/lab2","samuel","samuel");
    }
    public Connection getConnection(){
        return conn;
    }
    public static DBConnection getDBConnection()throws ClassNotFoundException,SQLException{
        if(dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }


}


