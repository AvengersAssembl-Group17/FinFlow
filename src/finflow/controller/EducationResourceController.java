package finflow.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class EducationResourceController implements Initializable {

    @FXML
    private AnchorPane VideoPane;

    @FXML
    private WebView videoWebView1;

    @FXML
    private WebView videoWebView2;

    @FXML
    private WebView videoWebView3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double videoWidth = 530.0;
        double videoHeight = 160.0;
        double spacing = 16.0; // Adjust as needed for spacing between videos

        // Calculate total height of videos and spacing
        double totalHeight = (videoHeight * 3) + (spacing * 2);

        // Calculate top anchor for the first video
        double topAnchor = (VideoPane.getPrefHeight() - totalHeight) / 2;

        // Load video 1
        WebEngine webEngine1 = videoWebView1.getEngine();
        webEngine1.loadContent("<iframe width=\"" + videoWidth + "\" height=\"" + videoHeight + "\" src=\"https://www.youtube.com/embed/4XZIv4__sQA\" frameborder=\"0\" allowfullscreen></iframe>");
        AnchorPane.setTopAnchor(videoWebView1, topAnchor + spacing*3);
        AnchorPane.setLeftAnchor(videoWebView1, 20.0);
        AnchorPane.setRightAnchor(videoWebView1, 20.0);

        // Load video 2
        WebEngine webEngine2 = videoWebView2.getEngine();
        webEngine2.loadContent("<iframe width=\"" + videoWidth + "\" height=\"" + videoHeight + "\" src=\"https://www.youtube.com/embed/spomyrwC3R8\" frameborder=\"0\" allowfullscreen></iframe>");
        AnchorPane.setTopAnchor(videoWebView2, topAnchor + videoHeight + spacing*4);
        AnchorPane.setLeftAnchor(videoWebView2, 20.0);
        AnchorPane.setRightAnchor(videoWebView2, 20.0);

        // Load video 3
        WebEngine webEngine3 = videoWebView3.getEngine();
        webEngine3.loadContent("<iframe width=\"" + videoWidth + "\" height=\"" + videoHeight + "\" src=\"https://www.youtube.com/embed/4j2emMn7UaI\" frameborder=\"0\" allowfullscreen></iframe>");
        AnchorPane.setTopAnchor(videoWebView3, topAnchor + (videoHeight + spacing*15));
        AnchorPane.setLeftAnchor(videoWebView3, 20.0);
        AnchorPane.setRightAnchor(videoWebView3, 20.0);
    }
}
