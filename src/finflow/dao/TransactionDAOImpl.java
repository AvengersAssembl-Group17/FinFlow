package finflow.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import finflow.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO{

	 private DatabaseConnection dbConnection;
	    
	 public TransactionDAOImpl(DatabaseConnection dbConnection) {
	        this.dbConnection = dbConnection;
	    }
	
	 public int getTransactionTypeId(String Type) {
	   	String query = "SELECT id FROM TransactionType where type = ?";
	   	int transactionTypeId = 0;
	   	try (Connection connection = dbConnection.getConnection()) {
	         try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        	 preparedStatement.setString(1, Type);
	        	 ResultSet resultSet = preparedStatement.executeQuery();            
	             while(resultSet.next()) {
	            	 transactionTypeId = resultSet.getInt("id");
	             }
	         }
        }catch (SQLException e) {
            e.printStackTrace();
        }
       	return transactionTypeId;
   }
	
   
	@Override
    public ResultSet getTransactionNamefromId(String id) {
        String query = "SELECT type FROM TransactionType WHERE id=?";
        ResultSet resultSet = null;
        try {
        	Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
	
	public int saveTransaction(Transaction transact) {
    	String query = "INSERT INTO Transaction(title, amount, type, date, notes, userId) VALUES (?,?,?,?,?,?)";
    	try (Connection connection = dbConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            	 preparedStatement.setString(1, transact.getTitle());
                 preparedStatement.setDouble(2, transact.getAmount());
                 preparedStatement.setInt(3, transact.getType());
                 preparedStatement.setDate(4, (Date) transact.getDate());
                 preparedStatement.setString(5, transact.getNotes());
                 preparedStatement.setInt(6, transact.getUserId());
                 int rowsAffected = preparedStatement.executeUpdate();
                 return rowsAffected > 0 ? 1: 0;
             }
         } catch (SQLException e) {
             e.printStackTrace();
             return 0;
         }
     }

	@Override
    public List<String> getTransactionTypesByCategory(String category) {
		String query = "SELECT type FROM transactionType WHERE category = ?";
        List<String> transactionTypes = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    transactionTypes.add(type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionTypes;
    }
}
