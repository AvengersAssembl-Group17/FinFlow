package finflow.controller;

import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.Constants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionItemController implements Initializable {

    @FXML
    private Label transactionAmount;

    @FXML
    private Label transactionDate;

    @FXML
    private Label transactionNotes;

    @FXML
    private Label transactionTitle;

    @FXML
    private TextField newAmountField;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private Transaction transaction;

    private TransactionHistoryController historyController;

    public void setData(Transaction trans) {
        this.transaction = trans;
        String formattedAmount = String.format(Constants.CURRENCY_FORMAT, trans.getAmount());
        transactionTitle.setText(trans.getTitle());
        transactionDate.setText(trans.getDate().toString());
        transactionNotes.setText(trans.getNotes());
        transactionAmount.setText(formattedAmount);
    }

    public void setHistoryController(TransactionHistoryController historyController) {
        this.historyController = historyController;
    }

    @FXML
    private void handleUpdateTransaction(ActionEvent event) {
        if (transaction == null) {
            System.err.println("Transaction is null.");
            return;
        }

        // Make the text field visible when clicking the update button
        newAmountField.setVisible(true);

        String newAmountText = newAmountField.getText().trim();

        double newAmount;
        try {
            newAmount = Double.parseDouble(newAmountText);
        } catch (NumberFormatException e) {
            System.err.println("Invalid new amount.");
            return;
        }

        TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
        transaction.setAmount(newAmount);
        int rowsAffected = transactionDAO.updateTransactionAmount(transaction.getTransactionId(), newAmount);
        if (rowsAffected > 0) {
            String formattedAmount = String.format(Constants.CURRENCY_FORMAT, newAmount);
            transactionAmount.setText(formattedAmount);
        } else {
            System.err.println("Failed to update transaction amount.");
        }
    }

    @FXML
    private void handleDeleteTransaction(ActionEvent event) {
        if (transaction == null) {
            System.err.println("Transaction or historyController is null.");
            return;
        }

        TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
        int rowsAffected = transactionDAO.deleteTransaction(transaction.getTransactionId());
        if (rowsAffected > 0) {
            System.out.println("Transaction deleted successfully.");
            
            //historyController.populateTransaction();
            // Repopulate the transaction history in the UI
        } else {
            System.err.println("Failed to delete transaction.");
        }
    }



    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic
    }
}
