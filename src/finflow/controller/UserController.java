package finflow.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController implements Initializable{
	
	 	@FXML
	    private TextField textAmount;

	    @FXML
	    private DatePicker date;

	    @FXML
	    private ComboBox<String> type;

	    @FXML
	    private Button addTransactionBtn;

	    @FXML
	    private Button buttonBack;
	    

	    private List<String> cities;
	   
	    private int currentIternaryID;
	    private int cityID;



	    private static UserController instance;

	    public UserController() {
	        instance = this;
	    }

	    /**
	     * @returns IternanryAddController
	     * */
	    public static UserController getInstance() {
	        return instance;
	    }
 
	    /**
	     * @param ActionEvent
	     * If user clicks on the back button, change windows back to Dashboard
	     * */
	    @FXML
	    void onClickBack(ActionEvent event) throws IOException {
	        Stage backTo = new Stage();
	        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/Menu.fxml"));
	        Scene scene = new Scene(root);
	        backTo.setScene(scene);
	        backTo.show();
	    }

	   
	    /**
	     * @param location url and resources
	     *  Required initialize method
	     *  setup
	     * */
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {

	    }

	    
	    /**
	     * @param ActionEvent
	     * @throws IOException
	     * Function is called when we want to add iternary
	     * */
	    @FXML
	    void addTransactionAction(ActionEvent event) throws IOException {

	}

}
