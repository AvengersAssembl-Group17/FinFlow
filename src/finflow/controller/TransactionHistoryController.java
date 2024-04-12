package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransactionHistoryController implements Initializable {

	@FXML
	private VBox transactionHistoryLayout;
	
	private TransactionDAO transDAO;
	
	private int activeUser;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	this.transDAO = new TransactionDAOImpl(new DatabaseConnection());
    	this.activeUser = LoginController.getInstance().activeID();
		populateTransaction();
	}
	
	
	
	public void populateTransaction() {
    	List<Transaction> recentTrans= new ArrayList<>(Transaction());
    	    		
    	for(int i=0; i<recentTrans.size();i++) {
    		FXMLLoader fxmlLoader = new FXMLLoader();
    		fxmlLoader.setLocation(getClass().getResource("/finflow/view/TransactionItem.fxml"));
    		try {
        		HBox hbox = fxmlLoader.load();
        		TransactionItemController tic = fxmlLoader.getController();
        		tic.setData(recentTrans.get(i));
        		transactionHistoryLayout.getChildren().add(hbox);
    		}catch(IOException e) {
    			e.printStackTrace();
    		}	
    	}
    }
    
    public List<Transaction> Transaction(){
    	List<Transaction> recentTrans= new ArrayList<>();
    	recentTrans = transDAO.getRecentTransactions(activeUser,0);
    	return recentTrans;
    }
}
