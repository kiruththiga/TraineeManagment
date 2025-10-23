package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DbConnection

{
    private static final String URL ="jdbc:mysql://localhost:3306/project";
    private static final String USER ="root";
    private static final String PASSWORD="admin";

static {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e){
        throw new ExceptionInInitializerError("MySql JDBC Driver not found:" + e.getMessage());
    }
}
public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
}
}
