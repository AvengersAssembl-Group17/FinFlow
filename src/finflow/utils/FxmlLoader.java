package finflow.utils;

import java.net.URL;

import finflow.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlLoader {
	public Pane getPage(String filename) {
    	Pane view = null;
    	try {
    		URL fileUrl = Main.class.getResource("view/"+filename+".fxml");
    		if(fileUrl==null) {
    			throw new java.io.FileNotFoundException("FXMl file can't be found");
    		}
    		view = new FXMLLoader().load(fileUrl);
    	}catch(Exception e){
    		System.out.println("No page "+filename+ "please check fxmlLoader");
    	}
    	return view;
    }
}
