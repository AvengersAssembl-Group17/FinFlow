package finflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import finflow.dao.DatabaseConnection;
import finflow.dao.UserDAO;
import finflow.dao.UserDAOImpl;
import finflow.model.User;
import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {


	    @FXML
	    private Button btnCreateAccount;

	    @FXML
	    private PasswordField txtConfirmPassword;

	    @FXML
	    private PasswordField txtPassword;

	    @FXML
	    private TextField txtPhone;

	    @FXML
	    private TextField txtfirstName;

	    @FXML
	    private TextField txtlastName;

	    @FXML
	    private TextField txtuserName;


    private UserDAO userDAO;

    public ProfileController() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Load user details and display them
    	this.userDAO = new UserDAOImpl(new DatabaseConnection());
    	try {
            ResultSet currentUser = userDAO.UserDetails(LoginController.getInstance().activeID());
            if (currentUser.next()) {
                txtfirstName.setText(currentUser.getString("fname"));
                txtlastName.setText(currentUser.getString("lname"));
                txtPhone.setText(currentUser.getString("phone"));
                txtuserName.setText(currentUser.getString("username"));
            }
          currentUser.close(); // Close ResultSet
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
