package finflow.controller;

import java.io.InputStream;
import java.net.URL;
//import java.time.Duration;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.util.Duration;
import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.model.TransactionDetails;
import finflow.utils.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.Optional;
import java.sql.Date;



public class TransactionItemController implements Initializable {

    @FXML
    private Label transactionAmount;

    @FXML
    private Label transactionDate;

    @FXML
    private Label transactionTitle;
    
    @FXML
    private ImageView transactionImg;

    @FXML
    private TextField newAmountField;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private Transaction transaction;
    
    private TransactionHistoryController historyController;
    
    private TransactionDAO transDAO;
    
    public TransactionItemController() {
    	this.transDAO = new TransactionDAOImpl(new DatabaseConnection());
    }
    
    public void setData(Transaction trans) {
    	this.transaction = trans;
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
    	transactionAmount.setText(formattedAmount);
    }

    public void setHistoryController(TransactionHistoryController historyController) {
        this.historyController = historyController;
    }

    
    @FXML
    private void handleUpdateTransaction() {
        Dialog<TransactionDetails> dialog = new Dialog<>();
        dialog.setTitle("Update Transaction Details");
        dialog.setHeaderText(null);

        ButtonType updateButtonType = new ButtonType("Update", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        DatePicker datePicker = new DatePicker();

        GridPane grid = new GridPane();
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Node updateButton = dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.setDisable(true);

        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButton.setDisable(newValue.trim().isEmpty() || amountField.getText().trim().isEmpty() || datePicker.getValue() == null);
        });

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!isValidNumber(newValue)) {
                    // Show dialog for invalid input
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid number for the amount.");
                    alert.showAndWait();

                    // Clear the invalid input
                    amountField.setText(oldValue);
                }
            }
            updateButton.setDisable(newValue.trim().isEmpty() || titleField.getText().trim().isEmpty() || datePicker.getValue() == null);
        });

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateButton.setDisable(newValue == null || titleField.getText().trim().isEmpty() || amountField.getText().trim().isEmpty());
        });

        Platform.runLater(titleField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new TransactionDetails(titleField.getText(), Double.parseDouble(amountField.getText()), datePicker.getValue());
            }
            return null;
        });

        Optional<TransactionDetails> result = dialog.showAndWait();
        result.ifPresent(details -> {
            String newTitle = details.getTitle();
            double newAmount = details.getAmount();
            LocalDate newDate = details.getDate();

            // Update transaction details in the database
            TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
            int rowsAffected = transactionDAO.updateTransactionDetails(transaction.getTransactionId(), newTitle, newAmount, Date.valueOf(newDate));

            if (rowsAffected > 0) {
                // If the update was successful, update the UI
                transaction.setTitle(newTitle);
                transaction.setAmount(newAmount);
                transaction.setDate(Date.valueOf(newDate));
                transactionTitle.setText(newTitle);
                transactionAmount.setText(String.format("$%.2f", newAmount));
                transactionDate.setText(newDate.toString());
                HomeController.getInstance().setTotalBalance();
                HomeController.getInstance().setTotalExpense();
                HomeController.getInstance().setTotalIncome();
            } else {
                // Handle the case where the update fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update transaction details.");
                alert.showAndWait();
            }
        });
    }

    private boolean isValidNumber(String input) {
        try {
            double amount = Double.parseDouble(input);
            return amount >= 0;
        } catch (NumberFormatException e) {
            return false;
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
               
                HomeController.getInstance().clearTransactionLayout();
                HomeController.getInstance().populateRecentTransaction();
                HomeController.getInstance().setTotalBalance();
                HomeController.getInstance().setTotalExpense();
                HomeController.getInstance().setTotalIncome();
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
