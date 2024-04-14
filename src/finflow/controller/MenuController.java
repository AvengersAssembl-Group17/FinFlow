package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import finflow.dao.DatabaseConnection;
import finflow.dao.TransactionDAO;
import finflow.dao.TransactionDAOImpl;
import finflow.model.Transaction;
import finflow.utils.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MenuController implements Initializable{
    
    @FXML
    private Button btnHome;
    
    @FXML
    private Button btnProfile;
    
    @FXML
    private Button btnTransactionHistory;
    
    @FXML
    private Button btnReports;
    
    @FXML
    private Button btnEducation;
    
    @FXML
    private Button btnLogout;
    
    @FXML
    private BorderPane mainPane;
    
    private static MenuController instance;
    
    private FxmlLoader fxmlLoader;
    
    private List<Transaction> history;
    
    private TransactionDAO transDAO;

    public MenuController() {
        instance = this;  
        this.fxmlLoader = new FxmlLoader();
        this.transDAO = new TransactionDAOImpl(new DatabaseConnection());
    }

    public static MenuController getInstance() {
        return instance;
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        Pane view = fxmlLoader.getPage("Home");
        mainPane.setCenter(view);
        
	}   
       
    @FXML
    void homeAction(ActionEvent event) throws IOException {
    	Pane view = fxmlLoader.getPage("Home");
        mainPane.setCenter(view);
    }
    
    @FXML
    void profileAction(ActionEvent event) throws IOException {
       Pane view = fxmlLoader.getPage("Profile");
       mainPane.setCenter(view);
    }
    
    @FXML
    void transactionHistoryAction(ActionEvent event) throws IOException {
        history = this.transDAO.getRecentTransactions(LoginController.getInstance().activeID(), 0);
    	if(history.isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Recent Transactions");
            alert.setHeaderText(null);
            alert.setContentText("You have no recent transactions yet. Start adding transactions to track your finances!");
            alert.showAndWait();
            return;
    	}	
    	Pane view = fxmlLoader.getPage("TransactionHistory");
        mainPane.setCenter(view);
    }
    
    @FXML
    void reportAction(ActionEvent event) throws IOException {
        history = this.transDAO.getRecentTransactions(LoginController.getInstance().activeID(), 0);
    	if(history.isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Recent Transactions");
            alert.setHeaderText(null);
            alert.setContentText("You have no recent transactions yet. Start adding transactions to track your finances!");
            alert.showAndWait();
            return;
    	}	
    	Pane view = fxmlLoader.getPage("Reports");
        mainPane.setCenter(view);
    }
    
    @FXML
    void educationAction(ActionEvent event) throws IOException {
    	Pane view = fxmlLoader.getPage("EducationalResource");
        mainPane.setCenter(view);
    }
   
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        btnLogout.getScene().getWindow().hide();
        Stage backToLogin = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/Login.fxml"));
        Scene scene = new Scene(root);
        Image image = new Image("finflow/images/logo2.png");
        backToLogin.getIcons().add(image);
        backToLogin.setScene(scene);
        backToLogin.setResizable(false);
        backToLogin.show();
    }
}

