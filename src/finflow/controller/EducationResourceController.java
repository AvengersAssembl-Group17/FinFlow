package finflow.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import finflow.utils.FxmlLoader;

public class EducationResourceController implements Initializable {

    @FXML
    private AnchorPane VideoPane;

    private FxmlLoader fxmlLoader;

    private BorderPane EducationResourceMain;

    private static EducationResourceController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fxmlLoader = new FxmlLoader();
        this.EducationResourceMain = EducationResourceController.getInstance().getMainPane();

        // Load the first video
        WebView webView1 = new WebView();
        WebEngine webEngine1 = webView1.getEngine();
        webEngine1.loadContent("<iframe width=\"290\" height=\"150\" src=\"https://www.youtube.com/embed/4XZIv4__sQA\" frameborder=\"0\" allowfullscreen></iframe>");
        VideoPane.getChildren().add(webView1);
        AnchorPane.setTopAnchor(webView1, 8.0);
        AnchorPane.setLeftAnchor(webView1, 7.0);

        // Load the second video
        WebView webView2 = new WebView();
        WebEngine webEngine2 = webView2.getEngine();
        webEngine2.loadContent("<iframe width=\"290\" height=\"150\" src=\"https://www.youtube.com/embed/spomyrwC3R8\" frameborder=\"0\" allowfullscreen></iframe>");
        VideoPane.getChildren().add(webView2);
        AnchorPane.setTopAnchor(webView2, 166.0); // Adjust the top position for the second video
        AnchorPane.setLeftAnchor(webView2, 7.0);

        // Load the third video
        WebView webView3 = new WebView();
        WebEngine webEngine3 = webView3.getEngine();
        webEngine3.loadContent("<iframe width=\"290\" height=\"150\" src=\"https://www.youtube.com/embed/4j2emMn7UaI\" frameborder=\"0\" allowfullscreen></iframe>");
        VideoPane.getChildren().add(webView3);
        AnchorPane.setTopAnchor(webView3, 324.0); // Adjust the top position for the third video
        AnchorPane.setLeftAnchor(webView3, 7.0);
    }

    public BorderPane getMainPane() {
        return EducationResourceMain;
    }

    public EducationResourceController() {
        instance = this;
    }

    public static EducationResourceController getInstance() {
        return instance;
    }
}
