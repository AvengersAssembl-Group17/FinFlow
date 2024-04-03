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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController implements Initializable{

    @FXML
    private Text txtWelcomeMessage;

    @FXML
    private Text txtTotalIncome;
    
    @FXML
    private Text txtTotalExpense;
        
    @FXML
    private Button btnAddIncome;
    
    @FXML
    private Button btnAddExpense;
    
    private static HomeController instance;
    
    public HomeController() {
        instance = this; 
        
    }

    public static HomeController getInstance() {
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