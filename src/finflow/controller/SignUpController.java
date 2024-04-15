
package finflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import finflow.dao.DatabaseConnection;
import finflow.dao.UserDAO;
import finflow.dao.UserDAOImpl;
import finflow.model.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Sign up controller for signing up user logic
 * */
public class SignUpController implements Initializable{

    @FXML
    private TextField txtfirstName;
    @FXML
    private TextField txtlastName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtuserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Button btnBackLogin;
    @FXML
    private Button btnCreateAccount;
    
    private UserDAO userDAO;
    
    public SignUpController() {
        this.userDAO = new UserDAOImpl(new DatabaseConnection());
        
    }
    
    /**
     * @param URL location, ResourceBundle resources
     * Required initialize method, setup
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * @param action event
     * @throws IOException
     * Function is invoked when user clicks on create account button, have included all the necessary validations
     * */
    @FXML
    void createAccount(ActionEvent event) throws IOException {   	
    	User user = new User();
        if (txtfirstName.getText().isEmpty() || txtuserName.getText().isEmpty() || txtlastName.getText().isEmpty() || txtPhone.getText().isEmpty()
                || txtPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all the fields").showAndWait();
        } else {
        	if (!txtfirstName.getText().matches("[a-zA-Z]+")) {
                new Alert(Alert.AlertType.ERROR, "First name should contain only alphabets").showAndWait();
        	} else if (!txtlastName.getText().matches("[a-zA-Z]+")) {
                new Alert(Alert.AlertType.ERROR, "Last name should contain only alphabets").showAndWait();
            // Both the password and confirm password should be equal
        	} else if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
                new Alert(Alert.AlertType.ERROR, "Password does not match with Confim Password !").showAndWait();
            } else if (txtPassword.getText().length() < 8) {
            	 new Alert(Alert.AlertType.ERROR, "Password length should be more than 8 !").showAndWait();
            } else if(!isPasswordValid(txtPassword.getText())) {
            	new Alert(Alert.AlertType.ERROR, "Password should have atleast one lower case, uppercase, digit and special character").showAndWait();
            } else if (!txtPhone.getText().matches("\\d+") || txtPhone.getText().length() != 10){
            	new Alert(Alert.AlertType.ERROR, "Please enter valid contact number").showAndWait();
            } else if(userDAO.userExists(txtuserName.getText())) {
            	new Alert(Alert.AlertType.ERROR, "Username already in use").showAndWait();
            } else {
            	user.setUsername(txtuserName.getText());
            	user.setLastName(txtlastName.getText());
            	user.setPassword(txtPassword.getText());
            	user.setPhone(txtPhone.getText());
            	user.setFirstName(txtfirstName.getText());
            	
                int status = userDAO.saveUser(user);
            	
            	if(status == 0) {
            		 new Alert(Alert.AlertType.ERROR, "User cannot be created !").showAndWait();
            		 return;
            	}
                
                // Send an alert to the user notifying them that their account has been created
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Account created successfully!");
                alert.showAndWait();

                // Hide the SignUp screen and go back to the Login page, allowing them to enter their credentials
                btnCreateAccount.getScene().getWindow().hide();
                Stage profileStage = new Stage();
                Parent profileRoot = FXMLLoader.load(getClass().getResource("/finflow/view/Login.fxml"));
                Scene profileScene = new Scene(profileRoot);
                Image image = new Image("finflow/images/logo2.png");
                profileStage.getIcons().add(image);
                profileStage.setScene(profileScene);
                profileStage.show();
            }
        }
    }


   
    /**
     * @param action event
     * @throws IOException
     * Login button to get back to main login page
     * */
    @FXML
    void loginAction(ActionEvent event) throws IOException {
        btnCreateAccount.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/Login.fxml"));
        Image image = new Image("finflow/images/logo2.png");
        login.getIcons().add(image);
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
        login.setResizable(false);
    }
    
    // To check if password meets requirement
    /**
     * @param password
     * @returns boolean for whether password matches or not 
     * */
    public static boolean isPasswordValid(String password) {
    	boolean upperCase = false;
    	boolean lowerCase = false;
    	boolean hasDigit = false;
    	boolean hasSpecialCharacter = false;
    	
    	String specialChars = "^&$@=#+%";
    	
    	for(char ch : password.toCharArray()) {
    		if(Character.isUpperCase(ch)) upperCase = true;
    		else if(Character.isLowerCase(ch)) lowerCase = true;
    		else if(Character.isDigit(ch)) hasDigit = true;
    		else if(specialChars.indexOf(ch) != -1) hasSpecialCharacter = true;
    	}
    	
    	return upperCase && lowerCase && hasDigit && hasSpecialCharacter;
    	
    }
}
