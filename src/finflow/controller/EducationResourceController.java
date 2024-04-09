package finflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

import finflow.utils.Constants;
import finflow.utils.FxmlLoader;

public class EducationResourceController {

    @FXML
    private Button btnVideoTutorials;
    
    @FXML
    private BorderPane EducationResourceMain;
    
    private String action;
    
    private FxmlLoader fxmlLoader;
    
    private static EducationResourceController instance;

    
    public BorderPane getMainPane() {
    	return EducationResourceMain;
    }
    
    public EducationResourceController() {
    	instance = this;
    }
    
    public static EducationResourceController getInstance() {
        return instance;
    }


    @FXML
    private void initialize() {
        // You can initialize elements here if needed
    	this.fxmlLoader = new FxmlLoader();
    }

    @FXML
    private void educationAction(ActionEvent event) throws IOException {
        // Load the EducationalResource.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("/finflow/view/EducationalResource.fxml"));
        Scene scene = new Scene(root);

        // Get the stage from the button's scene and hide it
        Stage stage = (Stage) btnVideoTutorials.getScene().getWindow();
        stage.hide();

        // Create a new stage for the educational resource window
        Stage educationStage = new Stage();
        educationStage.setScene(scene);
        educationStage.show();
        educationStage.setResizable(false);
    }

    @FXML
    void VideoTutorials(ActionEvent event) throws IOException {
    	this.action = Constants.ACTION_VIDEO;
		Pane view = this.fxmlLoader.getPage("VideoTutorials");
		EducationResourceMain.setCenter(view);
    }
}


