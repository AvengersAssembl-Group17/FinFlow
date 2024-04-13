package finflow.controller;

import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.Constants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
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
    
    private VBox transactionLayout;

    private TransactionHistoryController historyController;
    
    private HomeController homeController;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

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
        // Make the text field visible when clicking the update button
        newAmountField.setVisible(true);
    }

    @FXML
    private void handleNewAmountFieldKeyPress(KeyEvent event) {
    	if (event.getCode() == KeyCode.ENTER) {
            String newAmountText = newAmountField.getText().trim();

            double newAmount;
            try {
                newAmount = Double.parseDouble(newAmountText);

                // If parsing succeeds, update the transaction
                TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
                transaction.setAmount(newAmount);
                int rowsAffected = transactionDAO.updateTransactionAmount(transaction.getTransactionId(), newAmount);
                if (rowsAffected > 0) {
                    String formattedAmount = String.format(Constants.CURRENCY_FORMAT, newAmount);
                    transactionAmount.setText(formattedAmount);
                    newAmountField.setVisible(false); // Hide the text field
                } else {
                    System.err.println("Failed to update transaction amount.");
                }
            } catch (NumberFormatException e) {
                // Display an alert for invalid amount
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Amount");
                alert.setContentText("Please enter a valid amount.");
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void handleDeleteTransaction(ActionEvent event) {
        if (transaction == null) {
            System.err.println("Transaction or historyController is null.");
            return;
        }

        // Show confirmation dialog to the user
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Transaction");
        alert.setContentText("Are you sure you want to delete this transaction?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
            int rowsAffected = transactionDAO.deleteTransaction(transaction.getTransactionId());
            if (rowsAffected > 0) {
                System.out.println("Transaction deleted successfully.");
                
                // Clear the transaction layout
                //transactionLayout.getChildren().clear();
               
                HomeController.getInstance().clearTransactionLayout();
                // Update the UI
               HomeController.getInstance().populateRecentTransaction();
               
               if (historyController != null) {
                   historyController.clearTransactionHistoryLayout();
                   historyController.populateTransaction();
               }
            } else {
                // Display error message if deletion fails
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to delete transaction");
                errorAlert.setContentText("An error occurred while deleting the transaction. Please try again.");
                errorAlert.showAndWait();
            }
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
