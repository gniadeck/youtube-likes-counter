package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import configuration.Configuration;
import db.Data;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.Parent;

public class configController implements Initializable{

    @FXML
    private Button button_displayCounter;

    @FXML
    private Button button_edittext;

    @FXML
    private TextField input_apiKey;

    @FXML
    private DatePicker input_startDate;
    
    @FXML
    private Text text_errorMessage;

    @FXML
    private TextField input_channelID;

    @FXML
    private Button button_apiAccess;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Configuration config = new Configuration();
		if(Data.getCurrentConfiguration().getApi_key() != null) {
			input_apiKey.setText(Data.getCurrentConfiguration().getPrzelicznik());
		}
		if(Data.getCurrentConfiguration().getBegining_date() != null) {
			input_startDate.setValue(Data.getCurrentConfiguration().getBegining_date());
		}
		if(Data.getCurrentConfiguration().getChannel_id() != null) {
			input_channelID.setText(Data.getCurrentConfiguration().getChannel_id());
		}
		
		
		
		
		button_apiAccess.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				
				try {
					saveConfigurationFromScreen(false);
					button_apiAccess.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/apiScreen.fxml")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		});
		
		button_edittext.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				try {
					saveConfigurationFromScreen(false);
					Parent parent = FXMLLoader.load(getClass().getResource("/fxml/displayConfigurationScreen.fxml"));
					button_edittext.getScene().setRoot(parent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		});
		
		
		input_apiKey.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        System.out.println(input_apiKey.getText());
		    }
		});
		
		input_startDate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println(input_startDate.getValue().toString());
			}
		});
		
		button_displayCounter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if(!saveConfigurationFromScreen(true)) {
					return;
				}
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/counterScreen.fxml"));
					input_apiKey.getScene().setRoot(root);
					System.out.println("Wyświetlam licznik, zgodnie z konfiguracją: " + Data.getCurrentConfiguration());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.ERROR, "Wystąpił błąd " + e.getStackTrace().toString(), ButtonType.OK);
					e.printStackTrace();
				}
				
				
			}
		});
	
	}
    
    
    public boolean validateData(String apiKey, LocalDate date) {
    	
    	try{
    		
    		date.isBefore(LocalDate.now());
    		
    	} catch (NullPointerException e) {
    		Alert alert = new Alert(AlertType.ERROR, "Wpisz datę od której mają być zliczane filmy.", ButtonType.OK);
    		alert.showAndWait();
    		return false;
    	}
    	
    	if(!date.isBefore(LocalDate.now())) {
    			
				
				Alert alert = new Alert(AlertType.ERROR, "Podaj prawidłową datę (data nie może być z przeszłości)", ButtonType.OK);
				
				alert.showAndWait();
				
				

    		return false;
    	} else if (date == null) {
    		
    		Alert alert = new Alert(AlertType.ERROR, "Wpisz datę.", ButtonType.OK);
    		alert.showAndWait();
    		
    	}
    	
    	
     
    
    if(apiKey == "" || apiKey == null) {
    	 Alert alert = new Alert(AlertType.ERROR, "Wpisz przelicznik", ButtonType.OK);
    	 alert.showAndWait();
    	 return false;
    }
    
    return true;
    }
    
    public boolean saveConfigurationFromScreen(boolean validateData) {
    	
    	if(validateData == true) {
    		if(!validateData(input_apiKey.getText(), input_startDate.getValue())) {
    			return false;
    		}
    		
    	}
    	
			try {
				Data.getCurrentConfiguration().setBegining_date(input_startDate.getValue());
				Data.getCurrentConfiguration().setChannel_id(input_channelID.getText());
				Data.getCurrentConfiguration().setPrzelicznik(input_apiKey.getText());

				Data.saveData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR, "Nie udało się zapisać konfiguracji do pliku. Spróbuj otworzyć program z pozwoleniami administratora"
						+ ", lub skontaktuj się z autorem otwierając issue na githubie", ButtonType.OK);
				alert.showAndWait();
				return false;
			} catch (ClassNotFoundException e) {
				Alert alert = new Alert(AlertType.ERROR, "Nie udało się zapisać konfiguracji do pliku. Spróbuj otworzyć program z pozwoleniami administratora"
						+ ", lub skontaktuj się z autorem otwierając issue na githubie", ButtonType.OK);
				alert.showAndWait();
				e.printStackTrace();
				return false;
			}
			return true;
		}
    
    
    

}
