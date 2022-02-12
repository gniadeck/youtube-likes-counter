package gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class loadingController implements Initializable{

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextArea text_area;

    @FXML
    private ScrollPane scrollPane;

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public TextArea getText_area() {
		return text_area;
	}

	public void setText_area(TextArea text_area) {
		this.text_area = text_area;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println(text_area.getId());
		db.Data.setLoading(this);
		text_area.setEditable(false);
		text_area.appendText("Rozpoczynam ładowanie \n");
		
		
		
	}
    
    
    
    
    

}
