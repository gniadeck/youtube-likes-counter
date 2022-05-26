package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.crypto.Data;

import gui.Animations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import main.YTConnector;

public class MainScreenController implements Initializable{
	
    @FXML
    private Text plnSum;

    @FXML
    private Text thumbsUpSum;
    
    @FXML
    private Text text_header;

    @FXML
    private Text text_middle;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		validateAndSetFields();

		YTConnector YTConnector = new YTConnector();
		YTConnector.setMainScreenController(this);
		Animations animations = new Animations();
		animations.setMainScreenController(this);

		Thread ytAPI = new Thread(YTConnector);
		ytAPI.start();

	}

	public void throwErrorAndGetBackToMainScreen (String message) throws IOException {
		
		Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
		text_middle.getScene().setRoot(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/configScreen.fxml")));
		
		
	}

	private void validateAndSetFields() {
		if(db.Data.getCurrentConfiguration().getHeader_text() != null && db.Data.getCurrentConfiguration().getHeader_text() != "") {
			setText_header(db.Data.getCurrentConfiguration().getHeader_text());
		}
		if(db.Data.getCurrentConfiguration().getMiddle_text() != null && db.Data.getCurrentConfiguration().getMiddle_text() != "") {
			setText_middle(db.Data.getCurrentConfiguration().getMiddle_text());
		}
	}

	public  Text getPlnSum() {
		return plnSum;
	}

	public void setPlnSum(String text) {
		this.plnSum.setText(text);
	}

	public  Text getThumbsUpSum() {
		return thumbsUpSum;
	}

	public  void setThumbsUpSum(String text) {
		this.thumbsUpSum.setText(text);
	}


	public Text getText_header() {
		return text_header;
	}

	public void setText_header(String text_header) {
		this.text_header.setText(text_header);
	}

	public Text getText_middle() {
		return text_middle;
	}

	public void setText_middle(String text_middle) {
		this.text_middle.setText(text_middle);
	}

	

    
    
    

}
