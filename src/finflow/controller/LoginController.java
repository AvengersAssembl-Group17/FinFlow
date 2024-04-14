package finflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignUp;
    @FXML
    private Label lblForgotPassword; // Changed from Button to Label

    private int sessionUserID;
    private UserDAO userDAO;
    private static LoginController sessionInstance;

    // Constructor to initialize userDAO
    public LoginController() {
        sessionInstance = this;
    }
    /**
     * @return Login controller
     * */
    public static LoginController getInstance() {
        return sessionInstance;
    }
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
    	this.userDAO = new UserDAOImpl(new DatabaseConnection());
        
        // Set the "FORGOT PASSWORD?" label as a hyperlink
        lblForgotPassword.setOnMouseClicked(event -> {
            try {
                forgotpasswordAction(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
     
    
    /**
     * @param Action event
     * @throws IOException
     * function is invoked when user clicks on signup button
     * */
    @FXML
    public void signUpAction(ActionEvent event) throws IOException{
        btnLogin.getScene().getWindow().hide();
        Stage signupPage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/SignUp.fxml"));
        Scene scene = new Scene(root);
        Image image = new Image("finflow/images/logo2.png");
        signupPage.getIcons().add(image);
        signupPage.setScene(scene);
        signupPage.show();
        signupPage.setResizable(false);
    }

    
    
    @FXML
    public void forgotpasswordAction(MouseEvent event) throws IOException{
    	lblForgotPassword.getScene().getWindow().hide();
        Stage forgotPasswordPage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/ForgotPassword.fxml"));
        Scene scene = new Scene(root);
        Image image = new Image("finflow/images/logo2.png");
        forgotPasswordPage.getIcons().add(image);
        forgotPasswordPage.setScene(scene);
        forgotPasswordPage.show();
        forgotPasswordPage.setResizable(false);
    }
    /**
     * @param Action event
     * @throws IOException
     * Function will be invoked when user clicks on login button
     * */
    @FXML
    public void loginAction(ActionEvent event) throws IOException {
        if (txtUsername.getText().isBlank() || txtPassword.getText().isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all the fields").showAndWait();
        } else {
            try {
                ResultSet resultSet = userDAO.loginUser(txtUsername.getText(), txtPassword.getText());
                
                if (resultSet.next()) {
                    sessionUserID = resultSet.getInt("id");
                    btnLogin.getScene().getWindow().hide();
                    Stage main = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/Menu.fxml"));
                    Scene scene = new Scene(root);
                    Image image = new Image("finflow/images/logo2.png");
                    main.getIcons().add(image);
                    main.setScene(scene);
                    main.setResizable(false);
                    main.show();
                } else {
                    System.out.println("Error");
                    new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").showAndWait();
                    txtUsername.clear();
                    txtPassword.clear();
                }
                // Close the ResultSet after retrieving data
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
