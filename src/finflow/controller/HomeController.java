package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.Constants;
import finflow.utils.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    
    @FXML
    private VBox transactionLayout;
    
    private Label noTransactionsLabel;
    
    private static HomeController instance;
    
    private TransactionDAO transDAO;
    
    private FxmlLoader fxmlLoader;
    
    private int activeUser;
    
    private Double totalIncome =0.0;
    private Double totalExpense =0.0;
    private Double totalBalance =0.0;
    
    public VBox getTransactionLayout() {
        return transactionLayout;
    }

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
    	StringBuilder sb = new StringBuilder(Constants.WELCOME_PREFIX);
    	sb.append(username);
    	sb.append(Constants.WELCOME_SUFFIX);
        setWelcomeMessage(sb.toString());
        
        this.activeUser= LoginController.getInstance().activeID();
        
        setTotalIncome();
        setTotalExpense();
        setTotalBalance();
        
        noTransactionsLabel = new Label(Constants.NO_TRANSACTION_ALERT);
        noTransactionsLabel.setTextFill(Color.GREY);
        noTransactionsLabel.setVisible(false);
               
        populateRecentTransaction();  
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
    	String formattedTotalIncome = String.format(Constants.CURRENCY_FORMAT, this.totalIncome);
        txtTotalIncome.setText(formattedTotalIncome);
    }
    
    public void setTotalExpense() {
    	this.totalExpense = this.transDAO.getTotalExpenseUser(activeUser);
    	String formattedTotalExpense = String.format(Constants.CURRENCY_FORMAT, this.totalExpense);
        txtTotalExpense.setText(formattedTotalExpense);
    }
    
    public void setTotalBalance() {
    	this.totalBalance = this.transDAO.getAvailableBalanceUser(activeUser);
    	String formattedTotalBalance = String.format(Constants.CURRENCY_FORMAT, this.totalBalance);
    	this.txtTotalBalance.setText(formattedTotalBalance);
    }
    
    public List<Transaction> getRecenTransaction(){
    	List<Transaction> recentTrans= new ArrayList<>();
    	recentTrans = transDAO.getRecentTransactions(activeUser,Constants.RECENT_TRANSACTION_LIMIT);
    	return recentTrans;
    }
    
    public void populateRecentTransaction() {
        List<Transaction> recentTrans = new ArrayList<>(getRecenTransaction());
        Iterator<Transaction> iterator = recentTrans.iterator();
        
        if (recentTrans.isEmpty())
            showNoTransactionsLabel(true);
        else 
            showNoTransactionsLabel(false);

        while (iterator.hasNext()) {
            Transaction transaction = iterator.next();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/finflow/view/TransactionItem.fxml"));
            try {
                HBox hbox = fxmlLoader.load();
                TransactionItemController tic = fxmlLoader.getController();
                tic.setData(transaction);
                transactionLayout.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
    }

    
 // New method to clear the transaction layout
    public void clearTransactionLayout() {
        transactionLayout.getChildren().clear();
    }
    
    @FXML
    void addIncome(ActionEvent event) throws IOException {
    	this.action = Constants.ACTION_INCOME;
        Pane view = fxmlLoader.getPage("AddTransaction");
        homePane.setCenter(view);
    }
    
    @FXML
    void addExpense(ActionEvent event) throws IOException {   
    	this.action = Constants.ACTION_EXPENSE;
    	Pane view = fxmlLoader.getPage("AddTransaction");
        homePane.setCenter(view);
    }
    
    private void showNoTransactionsLabel(boolean show) {
        if (show && !transactionLayout.getChildren().contains(noTransactionsLabel)) {
        	transactionLayout.getChildren().add(noTransactionsLabel);
        	noTransactionsLabel.setVisible(true);
        } else if (!show && transactionLayout.getChildren().contains(noTransactionsLabel)) {
        	transactionLayout.getChildren().remove(noTransactionsLabel);
        }
    }
}