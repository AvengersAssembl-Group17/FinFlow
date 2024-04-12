package finflow.controller;
import java.net.URL;
import java.util.ResourceBundle;

import finflow.dao.DatabaseConnection;
import finflow.dao.UserDAO;
import finflow.dao.UserDAOImpl;
import finflow.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ForgotPasswordController implements Initializable {

    @FXML
    private PasswordField txtnewPassword;

    @FXML
    private PasswordField txtretypenewPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnUpdate;

    private UserDAO userDAO;

    @FXML
    private void UpdatePassword(ActionEvent event) {
        String username = txtUsername.getText();
        String newPassword = txtnewPassword.getText();
        String retypeNewPassword = txtretypenewPassword.getText();

        // Basic validation
        if (newPassword.isEmpty() || retypeNewPassword.isEmpty() || username.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
            return;
        }

        if (!newPassword.equals(retypeNewPassword)) {
            showAlert(AlertType.ERROR, "Error", "Password Mismatch", "Passwords do not match. Please retype.");
            return;
        }

        // Retrieve the user's ID based on the provided username
        int userID = userDAO.getUserIdByUsername(username);

        if (userID == -1) {
            showAlert(AlertType.ERROR, "Error", "User Not Found", "The provided username does not exist.");
            return;
        }

        User updatedUserPassword = new User();
        updatedUserPassword.setUsername(username);
        updatedUserPassword.setPassword(newPassword);
        updatedUserPassword.setId(userID);

        // Update the user's password in the database
        boolean passwordUpdated = userDAO.updateUserPassword(updatedUserPassword);

        if (passwordUpdated) {
            showAlert(AlertType.INFORMATION, "Success", "Password Updated",
                    "Your password has been updated successfully.");
        } else {
            showAlert(AlertType.ERROR, "Error", "Update Failed", "Failed to update password. Please try again later.");
        }
    }

    // Method to show alert dialog
    private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userDAO = new UserDAOImpl(new DatabaseConnection()); // Pass a valid DatabaseConnection instance
    }
}
