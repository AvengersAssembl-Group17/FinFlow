package finflow.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import finflow.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class FxmlLoader {
	public Pane getPage(String filename) {
    	Pane view = null;
    	try {
    		URL fileUrl = Main.class.getResource("view/"+filename+".fxml");
    		if(fileUrl==null) {
    			throw new java.io.FileNotFoundException("FXMl file can't be found");
    		}
    		view = FXMLLoader.load(fileUrl);
    	}catch(Exception e){
    		System.out.println("No page "+filename+ "please check fxmlLoader");
    	}
    	return view;
    }
	
	public void sceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException {
		AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("view/"+fxml+".fxml")));
		currentAnchorPane.getChildren().removeAll();
		currentAnchorPane.getChildren().setAll(nextAnchorPane);
	}
}