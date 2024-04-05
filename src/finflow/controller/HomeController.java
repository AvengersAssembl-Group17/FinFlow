package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
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
    private Text txtTotalBalance;
        
    @FXML
    private Button btnAddIncome;
    
    @FXML
    private Button btnAddExpense;
    
    private String action;
    
    @FXML
    private AnchorPane homeScreen;
    
    @FXML
    private BorderPane homePane;
    
    private static HomeController instance;
    
    private TransactionDAO transDAO;
    
    private FxmlLoader fxmlLoader;
    
    private int activeUser;
    
    private Double totalIncome =0.0;
    private Double totalExpense =0.0;
    private Double totalBalance =0.0;

    public HomeController() {
        instance = this; 
        this.fxmlLoader = new FxmlLoader();
    }

    public static HomeController getInstance() {
        return instance;
    }
    
    public BorderPane getMainPane() {
        return homePane;
    }
    
    public String actionPerformed() {
        return action;
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	this.transDAO = new TransactionDAOImpl(new DatabaseConnection());
    	
    	String username = LoginController.getInstance().username();
    	StringBuilder sb = new StringBuilder("Hello ");
    	sb.append(username);
    	sb.append(", welcome back!");
        setWelcomeMessage(sb.toString());
        
        this.activeUser= LoginController.getInstance().activeID();
        setTotalIncome();
        setTotalExpense();
        setTotalBalance();
	}   
   
    /**
     * @param user
     * Method sets welcomeMessage
     * */
    public void setWelcomeMessage(String message) {
        this.txtWelcomeMessage.setText(message);      
    }
    
    public void setTotalIncome() {
    	this.totalIncome = this.transDAO.getTotalIncomeUser(activeUser);
    	String formattedTotalIncome = String.format("$%.2f", this.totalIncome);
        txtTotalIncome.setText(formattedTotalIncome);
    }
    
    public void setTotalExpense() {
    	this.totalExpense = this.transDAO.getTotalExpenseUser(activeUser);
    	String formattedTotalExpense = String.format("$%.2f", this.totalExpense);
        txtTotalExpense.setText(formattedTotalExpense);
    }
    
    public void setTotalBalance() {
    	this.totalBalance = this.totalIncome - this.totalExpense;
    	String formattedTotalBalance = String.format("$%.2f", this.totalBalance);
    	this.txtTotalBalance.setText(formattedTotalBalance);
    }
    
    @FXML
    void addIncome(ActionEvent event) throws IOException {
    	this.action = "Income";
        Pane view = fxmlLoader.getPage("AddTransaction");
        homePane.setCenter(view);
    }
    
    @FXML
    void addExpense(ActionEvent event) throws IOException {   
    	this.action = "Expense";
    	Pane view = fxmlLoader.getPage("AddTransaction");
        homePane.setCenter(view);
    }
}