package gui;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application{

	@Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/configScreen.fxml"));
        Stage stage = new Stage();
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
		primaryStage.setOnCloseRequest(e -> System.exit(0));
	}
	
	public static void main(String[] args)
	        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
	    	

		
		
	    	try {
	    		launch(args);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	}
	
	
	

}
