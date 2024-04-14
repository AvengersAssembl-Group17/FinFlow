package finflow.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection extends Config {

    Connection dbConnection;
    
    public DatabaseConnection() {
    	this.dbConnection = getConnection();
    }

    public Connection getConnection(){
        String connectionString = "jdbc:mysql://" + dbhost + ":" + dbport + "/" + dbname + "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.dbConnection = DriverManager.getConnection(connectionString, dbuser, dbpass);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
			e.printStackTrace();
		}
        return this.dbConnection;
    }
}
