package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardController implements Initializable{

    @FXML
    private Text txtWelcomeMessage;

    @FXML
    private Button btnProfile;
    
    @FXML
    private Button btnHome;
    
    @FXML
    private Button btnTransactionHistory;
    
    @FXML
    private Button btnReports;
    
    @FXML
    private Button btnEducation;
    
    @FXML
    private Button btnLogout;
    
    @FXML
    private Text txtTotalIncome;
    
    @FXML
    private Text txtTotalExpense;
        
    @FXML
    private Button btnAddIncome;
    
    @FXML
    private Button btnAddExpense;
    

    private static DashboardController instance;

    public DashboardController() {
        instance = this; 
        
    }

    public static DashboardController getInstance() {
        return instance;
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	String username = LoginController.getInstance().username();
    	StringBuilder sb = new StringBuilder("Hello ");
    	sb.append(username);
    	sb.append(", welcome back!");
        setWelcomeMessage(sb.toString());
	}   
   
    /**
     * @param user
     * Method sets welcomeMessage
     * */
    public void setWelcomeMessage(String message) {
        this.txtWelcomeMessage.setText(message);      
    }
     
    /**
     * @param action event
     * @throws IOException
     * Logout logic
     * */
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        btnLogout.getScene().getWindow().hide();
        Stage backToLogin = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/Login.fxml"));
        Scene scene = new Scene(root);
        Image image = new Image("finflow/images/logo2.png");
        backToLogin.getIcons().add(image);
        backToLogin.setScene(scene);
        backToLogin.show();
    }
    
    @FXML
    void addIncome(ActionEvent event) throws IOException {   
        Stage newIternary = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/AddTransaction.fxml"));
        Scene scene = new Scene(root);
        newIternary.setScene(scene);
        newIternary.show();
    }
    
    @FXML
    void addExpense(ActionEvent event) throws IOException {   
        Stage newIternary = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/AddTransaction.fxml"));
        Scene scene = new Scene(root);
        newIternary.setScene(scene);
        newIternary.show();
    }
}

