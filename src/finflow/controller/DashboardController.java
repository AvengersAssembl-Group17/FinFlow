package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardController implements Initializable{

    @FXML
    private Text txtUsername;

    @FXML
    private Button btnProfile;
    
    @FXML
    private Button btnHome;
    
    @FXML
    private Button btnTransactionHistory;
    
    @FXML
    private Button btnReports;
    
    @FXML
    private Button btnEducation;
    
    @FXML
    private Button btnLogout;

    private static DashboardController instance;

    public DashboardController() {
        instance = this; 
        
    }

    public static DashboardController getInstance() {
        return instance;
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        setUsername(LoginController.getInstance().username());
	}   
   
    /**
     * @param user
     * Method sets username
     * */
    public void setUsername(String user) {
        this.txtUsername.setText(user);      
    }
     
    /**
     * @param action event
     * @throws IOException
     * Logout logic
     * */
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        btnLogout.getScene().getWindow().hide();
        Stage backToLogin = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/Login.fxml"));
        Scene scene = new Scene(root);
        backToLogin.setScene(scene);
        backToLogin.show();
    }
}

