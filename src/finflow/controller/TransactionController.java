package finflow.controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAOImpl;
import finflow.dao.TransactionDAO;
import finflow.model.Transaction;
import finflow.utils.Constants;
import finflow.utils.FxmlLoader;

public class TransactionController implements Initializable{
	
 	@FXML
    private TextField transactionTitle;
 	
 	@FXML
    private TextField transactionAmount;

    @FXML
    private ComboBox<String> transactionType;
    
    @FXML
    private DatePicker transactionDate;
    
    @FXML
    private TextField transactionReference;

    @FXML
    private Button addTransactionBtn;
    
    @FXML
    private Label screenTitle;

    @FXML
    private Button buttonBack;
    
    @FXML
    private AnchorPane addTransaction;
    
    private FxmlLoader fxmlLoader;

    private List<String> transactionTypes;
    
    private BorderPane homePane;
       
    private static TransactionController instance;
    
    private TransactionDAO transactionDAO;
    
    private int activeID;
    
    private String action;

    public TransactionController() {
        instance = this;
        this.fxmlLoader= new FxmlLoader();
    }

    public static TransactionController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	 this.transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
    	 homePane = HomeController.getInstance().getMainPane();  
    	 activeID= LoginController.getInstance().activeID();
    	 action = HomeController.getInstance().actionPerformed();
    	 
    	 transactionTypes = new ArrayList<>();

    	 if(action.equalsIgnoreCase(Constants.ACTION_INCOME)) {
    	        List<String> incomeTypes = transactionDAO.getTransactionTypesByCategory(Constants.ACTION_INCOME);
    	        transactionTypes.addAll(incomeTypes);
    	  } else if(action.equalsIgnoreCase(Constants.ACTION_EXPENSE)){
    	        List<String> expenseTypes = transactionDAO.getTransactionTypesByCategory(Constants.ACTION_EXPENSE);
    	        transactionTypes.addAll(expenseTypes);
    	  }
         transactionType.setItems(FXCollections.observableArrayList(transactionTypes));
    }

	@FXML
    void onClickBack(ActionEvent event) throws IOException {
    	Pane view = fxmlLoader.getPage("Home");
        homePane.setCenter(view);
    }


    @FXML
    public void addTransactionAction(ActionEvent event) throws IOException {  
    	Double availableBalance = transactionDAO.getAvailableBalanceUser(activeID);
    	Double transAmount =0.0;
    	if (transactionTitle.getText().isEmpty()){
        	new Alert(Alert.AlertType.ERROR, "Please enter transaction title").showAndWait();
        	return;
        }
    	
    	if(transactionAmount.getText().isEmpty()) {
    		new Alert(Alert.AlertType.ERROR, "Please enter transaction amount").showAndWait();
        	return;
    	}
    	try {
    		transAmount = Double.parseDouble(transactionAmount.getText());
    	}catch(NumberFormatException e) {
    		new Alert(Alert.AlertType.ERROR, "Please enter valid transaction amount").showAndWait();
        	return;
    	}

    	if(transactionType.getValue() == null){
        	new Alert(Alert.AlertType.ERROR, "Please select transaction type").showAndWait();
        	return;
        }  
    	
        if (transactionDate.getValue() == null) {
        	new Alert(Alert.AlertType.ERROR, "Please enter transaction date").showAndWait();
        	return;
        }
        
        LocalDate localTransactionDate = transactionDate.getValue();
    	java.sql.Date sqlDate = Date.valueOf(localTransactionDate);
    	
        Transaction transaction = new Transaction();
        transaction.setTitle(transactionTitle.getText());
        transaction.setAmount(Double.parseDouble(transactionAmount.getText()));
        
        transaction.setType(transactionDAO.getTransactionTypeId(transactionType.getValue()));
        
        transaction.setDate(sqlDate);
        transaction.setNotes(transactionReference.getText());
        transaction.setUserId(activeID);
        int status=0;
        ButtonType result = null;
        
        if (transAmount > availableBalance && action.equalsIgnoreCase(Constants.ACTION_EXPENSE)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Transaction Info");
            alert.setHeaderText("Transaction Amount Exceeds Available Funds");
            alert.setContentText("Click OK to proceed, or Cancel to abort.");
            
            result = alert.showAndWait().orElse(ButtonType.CANCEL);
         }
        
        if (action.equalsIgnoreCase(Constants.ACTION_INCOME) || 
        		(action.equalsIgnoreCase(Constants.ACTION_EXPENSE) && result == ButtonType.OK)) {
            status =  transactionDAO.saveTransaction(transaction);  
        } else {
            System.out.println("User clicked Cancel or closed the dialog, aborting...");
            return;
        }
        
        if(status == 0) {
   		 new Alert(Alert.AlertType.ERROR, "Transaction could not be added! Please try again later.").showAndWait();
   		 return;
        }            
       
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Transaction Added Successfully");
        alert.showAndWait();
        
        Pane view = fxmlLoader.getPage("Home");
        homePane.setCenter(view);
    }
}
