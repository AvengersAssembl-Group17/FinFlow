package finflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import finflow.dao.DatabaseConnection;
import finflow.dao.UserDAO;
import finflow.dao.UserDAOImpl;
/**
 * Login controller class
 * */
public class LoginController implements Initializable{

  
    // Global variable used to track which user is logged in
    private int sessionUserID;

    private static LoginController sessionInstance;
    
    private UserDAO userDAO;

    // Constructor to initialize userDAO
    public LoginController() {
        sessionInstance = this;
        this.userDAO = new UserDAOImpl(new DatabaseConnection());
        
    }
    /**
     * @return Login controller
     * */
    public static LoginController getInstance() {
        return sessionInstance;
    }

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignUp;

    /**
     * @return Logged in user id
     * */
    public int activeID() {
        return sessionUserID;
    }

    /**
     * @return String username
     * */
    public String username() {
        return txtUsername.getText();
    }

 
    /**
     * @param url location, resources
     * Required initialize method
     * setup
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	this.userDAO = new UserDAOImpl(null);
    }
     
    
    /**
     * @param Action event
     * @throws IOException
     * function is invoked when user clicks on signup button
     * */
    @FXML
    public void signUpAction(ActionEvent event) throws IOException{
        btnLogin.getScene().getWindow().hide();

        // Load in the Sign Up page, control is now transferred to SignUp.fxml
        Stage signupPage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/SignUp.fxml"));
        Scene scene = new Scene(root);
        signupPage.setScene(scene);
        signupPage.show();
        signupPage.setResizable(false);
    }


    /**
     * @param Action event
     * @throws IOException
     * Function will be invoked when user clicks on login button
     * */
    @FXML
    public void loginAction(ActionEvent event) throws IOException{
    	if(txtUsername.getText().isBlank() || txtPassword.getText().isBlank()){
    		new Alert(Alert.AlertType.ERROR, "Please enter all the fields").showAndWait();
    	} else {
        try {
            int count = 0;
            ResultSet resultSet = userDAO.loginUser(txtUsername.getText(), txtPassword.getText());

            while(resultSet.next()) {
                count += 1;
                sessionUserID = resultSet.getInt("id");
            }

            if(count == 1) {
                btnLogin.getScene().getWindow().hide();
                Stage dashboardPage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/Dashboard.fxml"));
                Scene scene = new Scene(root);
                dashboardPage.setScene(scene);
                dashboardPage.show();
            } else {
                System.out.println("Error");
                new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").showAndWait();
                txtUsername.clear();
                txtPassword.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    	}
    }
}
