
package finflow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import finflow.model.User;


public class UserDAOImpl implements UserDAO {

    private DatabaseConnection dbConnection;
    
    public UserDAOImpl(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    @Override
    public int saveUser(User user) {
        String query = "INSERT INTO user(username, password, fname, lname, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getFirstName());
                preparedStatement.setString(4, user.getLastName());
                preparedStatement.setString(5, user.getPhone());
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0 ? 1: 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public boolean userExists(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection connection = dbConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection connection = dbConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public ResultSet loginUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username=? AND password=?";
        ResultSet resultSet = null;
        try {
        	Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultSet;
    }

    @Override
    public ResultSet UserDetails(int userid)
    {
  
        String query = "SELECT * FROM user WHERE id=?";
        ResultSet resultSet = null;
        try {
        	Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setInt(1, userid);
            resultSet = preparedStatement.executeQuery();            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultSet;
    }

}
