package finflow.controller;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
	
//	public void populateTransaction() {
//    	List<Transaction> recentTrans= new ArrayList<>(Transaction());
//    	    		
//    	for(int i=0; i<recentTrans.size();i++) {
//    		FXMLLoader fxmlLoader = new FXMLLoader();
//    		fxmlLoader.setLocation(getClass().getResource("/finflow/view/TransactionItem.fxml"));
//    		try {
//        		HBox hbox = fxmlLoader.load();
//        		TransactionItemController tic = fxmlLoader.getController();
//        		tic.setData(recentTrans.get(i));
//        		transactionHistoryLayout.getChildren().add(hbox);
//    		}catch(IOException e) {
//    			e.printStackTrace();
//    		}	
//    	}
//    }
	
	public void populateTransaction() {
	    List<Transaction> recentTrans = Transaction();

	    for (int i = 0; i < recentTrans.size(); i++) {
	        FXMLLoader fxmlLoader = new FXMLLoader();
	        fxmlLoader.setLocation(getClass().getResource("/finflow/view/TransactionItem.fxml"));
	        try {
	            HBox hbox = fxmlLoader.load();
	            TransactionItemController tic = fxmlLoader.getController();
	            tic.setData(recentTrans.get(i));
	            tic.setHistoryController(this); // Set the historyController property
	            transactionHistoryLayout.getChildren().add(hbox);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	
	protected void removeTransactionUIElement(Transaction transaction) {
	    System.out.println("Removing transaction UI element for transaction: " + transaction.getTransactionId());

	    Platform.runLater(() -> {
	        // Loop through the children of transactionHistoryLayout to find the UI element representing the transaction
	        Iterator<Node> iterator = transactionHistoryLayout.getChildren().iterator();
	        while (iterator.hasNext()) {
	            Node node = iterator.next();
	            if (node instanceof HBox) {
	                HBox hbox = (HBox) node;
	                TransactionItemController tic = (TransactionItemController) hbox.getProperties().get("controller");
	                if (tic != null && tic.getTransaction().equals(transaction)) {
	                    // Remove the UI element from the layout
	                    iterator.remove();
	                    System.out.println("Transaction UI element removed successfully.");
	                    break; // Exit the loop after removing the UI element
	                }
	            }
	        }
	    });
	}

    public List<Transaction> Transaction(){
    	List<Transaction> recentTrans= new ArrayList<>();
    	recentTrans = transDAO.getRecentTransactions(activeUser,0);
    	return recentTrans;
    }
}
