package gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class LoadingController implements Initializable{

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextArea text_area;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		    
		System.out.println(text_area.getId());
		db.Data.setLoadingController(this);
		text_area.setEditable(false);
		text_area.appendText("Rozpoczynam ładowanie \n");

	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public TextArea getTextArea() {
		return text_area;
	}

	public void setTextArea(TextArea text_area) {
		this.text_area = text_area;
	}


}

		
		
		





