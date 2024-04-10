package finflow.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    
    private TransactionDAO transDAO;
    
    public TransactionItemController() {
    	this.transDAO = new TransactionDAOImpl(new DatabaseConnection());
    }
    
    public void setData(Transaction trans) {
    	String formattedAmount = String.format(Constants.CURRENCY_FORMAT, trans.getAmount());
    	String transactionType = transDAO.getTransactionTypeNameById(trans.getType());
    	System.out.println(transactionType);
        String imagePath = "/finflow/images/" + Constants.IMAGE_MAP.get(transactionType) + ".png";

        System.out.println(transactionType);
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image transImg = new Image(imageStream);
            transactionImg.setImage(transImg);
        }else {
        	System.out.println("No Image found for "+transactionType + ", setting default image");
        }
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