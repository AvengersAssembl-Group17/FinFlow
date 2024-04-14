package finflow.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.Constants;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

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
    	transactionNotes.setText(trans.getNotes());
    	transactionAmount.setText(formattedAmount);
    }

    public void setHistoryController(TransactionHistoryController historyController) {
        this.historyController = historyController;
    }
    
//    @FXML
//    private void handleUpdateTransaction() {
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Update Transaction Amount");
//        dialog.setHeaderText(null);
//        dialog.setContentText("Please enter the new amount:");
//
//        Optional<String> result = dialog.showAndWait();
//        result.ifPresent(amountStr -> {
//            try {
//                double newAmount = Double.parseDouble(amountStr);
//
//                // Update transaction amount in the database
//                TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
//                int rowsAffected = transactionDAO.updateTransactionAmount(transaction.getTransactionId(), newAmount);
//
//                if (rowsAffected > 0) {
//                    // If the update was successful, update the UI
//                    transaction.setAmount(newAmount);
//                    transactionAmount.setText(String.format("$%.2f", newAmount));
//                    HomeController.getInstance().setTotalBalance();
//                    HomeController.getInstance().setTotalExpense();
//                    HomeController.getInstance().setTotalIncome();
//                } else {
//                    // Handle the case where the update fails
//                    Alert alert = new Alert(AlertType.ERROR);
//                    alert.setTitle("Error");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Failed to update transaction amount.");
//                    alert.showAndWait();
//                }
//            } catch (NumberFormatException e) {
//                // Handle the case where the input amount is invalid
//                Alert alert = new Alert(AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText(null);
//                alert.setContentText("Invalid amount format!");
//                alert.showAndWait();
//            }
//        });
//    }
    
    @FXML
    private void handleUpdateTransaction() {
        Dialog<Pair<String, Double>> dialog = new Dialog<>();
        dialog.setTitle("Update Transaction Details");
        dialog.setHeaderText(null);

        ButtonType updateButtonType = new ButtonType("Update", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        GridPane grid = new GridPane();
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Node updateButton = dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.setDisable(true);

        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButton.setDisable(newValue.trim().isEmpty() || amountField.getText().trim().isEmpty());
        });

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButton.setDisable(newValue.trim().isEmpty() || titleField.getText().trim().isEmpty());
        });

        Platform.runLater(titleField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new Pair<>(titleField.getText(), Double.parseDouble(amountField.getText()));
            }
            return null;
        });

        Optional<Pair<String, Double>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            String newTitle = pair.getKey();
            double newAmount = pair.getValue();

            // Update transaction details in the database
            TransactionDAO transactionDAO = new TransactionDAOImpl(new DatabaseConnection());
            int rowsAffected = transactionDAO.updateTransactionDetails(transaction.getTransactionId(), newTitle, newAmount);

            if (rowsAffected > 0) {
                // If the update was successful, update the UI
                transaction.setTitle(newTitle);
                transaction.setAmount(newAmount);
                transactionTitle.setText(newTitle);
                transactionAmount.setText(String.format("$%.2f", newAmount));
                HomeController.getInstance().setTotalBalance();
                HomeController.getInstance().setTotalExpense();
                HomeController.getInstance().setTotalIncome();
            } else {
                // Handle the case where the update fails
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update transaction details.");
                alert.showAndWait();
            }
        });
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
