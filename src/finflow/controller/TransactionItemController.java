package finflow.controller;

import java.net.URL;
import java.util.ResourceBundle;

import finflow.model.Transaction;
import finflow.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class TransactionItemController implements Initializable{

    @FXML
    private Label transactionAmount;

    @FXML
    private Label transactionDate;

    @FXML
    private ImageView transactionImg;

    @FXML
    private Label transactionNotes;

    @FXML
    private Label transactionTitle;
    
    public void setData(Transaction trans) {
    	String formattedAmount = String.format(Constants.CURRENCY_FORMAT, trans.getAmount());
    	transactionTitle.setText(trans.getTitle());
    	transactionDate.setText(trans.getDate().toString());
    	transactionNotes.setText(trans.getNotes());
    	transactionAmount.setText(formattedAmount);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}