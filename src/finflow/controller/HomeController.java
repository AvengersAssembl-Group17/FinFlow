package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import finflow.utils.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    
    @FXML
    private AnchorPane homeScreen;
    
    @FXML
    private BorderPane homePane;
    
    private static HomeController instance;
    
    private FxmlLoader fxmlLoader;
    
    public HomeController() {
        instance = this; 
        this.fxmlLoader = new FxmlLoader();
        
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
        Pane view = fxmlLoader.getPage("AddTransaction");
        homePane.setCenter(view);
    }
    
    @FXML
    void addExpense(ActionEvent event) throws IOException {   
    	Pane view = fxmlLoader.getPage("AddTransaction");
        homePane.setCenter(view);
    }
}