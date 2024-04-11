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

        // Define dimensions for the videos
        double videoWidth = 567.0;
        double videoHeight1 = 133.0;
        double videoHeight2 = 148.0;
        double videoHeight3 = 140.0;
        double spacing = 8.0; // Adjust as needed for spacing between videos

        // Calculate available height for videos
        double availableHeight = VideoPane.getPrefHeight() - (4 * spacing); // Subtracting spacing between videos

        // Calculate dimensions for videos based on available height
        double actualHeight1 = Math.min(availableHeight / 3, videoHeight1);
        double actualHeight2 = Math.min(availableHeight / 3, videoHeight2);
        double actualHeight3 = Math.min(availableHeight / 3, videoHeight3);

        // Load the first video
        WebView webView1 = new WebView();
        WebEngine webEngine1 = webView1.getEngine();
        webEngine1.loadContent("<iframe width=\"" + videoWidth + "\" height=\"" + actualHeight1 + "\" src=\"https://www.youtube.com/embed/4XZIv4__sQA\" frameborder=\"0\" allowfullscreen></iframe>");
        VideoPane.getChildren().add(webView1);
        AnchorPane.setTopAnchor(webView1, spacing);
        AnchorPane.setLeftAnchor(webView1, 7.0);

        // Load the second video
        WebView webView2 = new WebView();
        WebEngine webEngine2 = webView2.getEngine();
        webEngine2.loadContent("<iframe width=\"" + videoWidth + "\" height=\"" + actualHeight2 + "\" src=\"https://www.youtube.com/embed/spomyrwC3R8\" frameborder=\"0\" allowfullscreen></iframe>");
        VideoPane.getChildren().add(webView2);
        AnchorPane.setTopAnchor(webView2, actualHeight1 + (2 * spacing)); // Add spacing and height of first video
        AnchorPane.setLeftAnchor(webView2, 7.0);

        // Load the third video
        WebView webView3 = new WebView();
        WebEngine webEngine3 = webView3.getEngine();
        webEngine3.loadContent("<iframe width=\"" + videoWidth + "\" height=\"" + actualHeight3 + "\" src=\"https://www.youtube.com/embed/4j2emMn7UaI\" frameborder=\"0\" allowfullscreen></iframe>");
        VideoPane.getChildren().add(webView3);
        AnchorPane.setTopAnchor(webView3, actualHeight1 + actualHeight2 + (3 * spacing)); // Add spacing and height of first two videos
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