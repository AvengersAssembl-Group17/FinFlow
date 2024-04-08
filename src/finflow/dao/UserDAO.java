package finflow.dao;

import java.sql.ResultSet;

import finflow.model.User;

public interface UserDAO {
    int saveUser(User user);
    boolean userExists(String username);
    boolean authenticateUser(String username, String password);
    ResultSet loginUser(String username, String password);
    ResultSet UserDetails(int userid); // New method to retrieve user details
}
