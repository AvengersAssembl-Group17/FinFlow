package finflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import finflow.utils.FxmlLoader;


public class VideoTutorialsController implements Initializable {

    //@FXML
    //private WebView WebView;
	
	@FXML
	private AnchorPane VideoPane;
	
	private FxmlLoader fxmlLoader;
	
    private BorderPane EducationResourceMain;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	this.fxmlLoader = new FxmlLoader();
    	this.EducationResourceMain= EducationResourceController.getInstance().getMainPane();
      WebView webView = new WebView();
      WebEngine webEngine = webView.getEngine();
      webEngine.loadContent("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/4XZIv4__sQA\" frameborder=\"0\" allowfullscreen></iframe>");
      // Add the WebView to the AnchorPane
      VideoPane.getChildren().add(webView);

      // Set layout constraints to make the WebView fill the AnchorPane
      AnchorPane.setTopAnchor(webView, 0.0);
      AnchorPane.setBottomAnchor(webView, 0.0);
      AnchorPane.setLeftAnchor(webView, 0.0);
      AnchorPane.setRightAnchor(webView, 0.0);
    }
    
    @FXML
    void onClickBack(ActionEvent event) throws IOException {
    	Pane view = fxmlLoader.getPage("EducationalResource");
    	EducationResourceMain.setCenter(view);
    }
}


