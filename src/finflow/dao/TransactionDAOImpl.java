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
	
	public Double getTotalIncomeUser(int userId) {
		String query = "SELECT SUM(amount) AS total_income FROM transaction " +
                "WHERE userId = ? AND type IN (SELECT id FROM transactionType WHERE category = 'Income')";		
		double totalIncome = 0.0;
	    try (Connection connection = dbConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	    	preparedStatement.setInt(1, userId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                totalIncome = resultSet.getDouble("total_income");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
	    return totalIncome;
	}
	
	public Double getTotalExpenseUser(int userId) {
		String query = "SELECT SUM(amount) AS total_expense FROM transaction " +
                "WHERE userId = ? AND type IN (SELECT id FROM transactionType WHERE category = 'Expense')";
		double totalExpense = 0.0;
	    try (Connection connection = dbConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	    	preparedStatement.setInt(1, userId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	totalExpense = resultSet.getDouble("total_expense");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
	    return totalExpense;
	}
	
	public List<Transaction> getRecentTransactions(int userId, int limit) {
        List<Transaction> recentTransactions = new ArrayList<>();
        String query = "SELECT * FROM `transaction` t JOIN transactionType tt ON t.type = tt.id where t.userId=? ORDER BY t.date DESC";
        if (limit > 0) {
        	query += " LIMIT ?";
        }
        try (Connection connection = dbConnection.getConnection();
   	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            if (limit > 0) {
            	preparedStatement.setInt(2, limit);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt("id"));
                transaction.setUserId(resultSet.getInt("userId"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setTitle(resultSet.getString("title"));
                transaction.setType(resultSet.getInt("type"));
                transaction.setDate(resultSet.getDate("date"));
                transaction.setNotes(resultSet.getString("notes"));
                recentTransactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recentTransactions;
    }

}
