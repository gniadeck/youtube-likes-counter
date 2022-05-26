package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import com.google.api.client.util.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import db.Data;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ApiController implements Initializable {

    @FXML
    private Button button_saveAndQuit;

    @FXML
    private TextField input_clientId;

    @FXML
    private TextField input_clientSecret;

    @FXML
    private TextField input_projectId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

		setAvailableFields();
		button_saveAndQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (validateData(input_clientId.getText(), input_clientSecret.getText(), input_projectId.getText())) {

                    try {

                        writeToConfigJson("client_secret", input_clientSecret.getText());
                        writeToConfigJson("project_id", input_projectId.getText());
                        writeToConfigJson("client_id", input_clientId.getText());

                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }

					Data.getCurrentConfiguration().setApi_key(input_clientSecret.getText());
					Data.getCurrentConfiguration().setProject_id(input_projectId.getText());
					Data.getCurrentConfiguration().setClient_id(input_clientId.getText());

					try {
						Data.saveData();

                        button_saveAndQuit.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/configScreen.fxml")));
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
					System.out.println("Zapisano konfiguracje " + Data.getCurrentConfiguration());
				} else {
                    button_saveAndQuit.setText("Nie wpisa�e� prawid�owej warto�ci.");
                }
            }
        });
    }

	private void setAvailableFields() {
		if (Data.getCurrentConfiguration().getClient_id() != null) {
			input_clientId.setText(Data.getCurrentConfiguration().getClient_id());
		}
		if (Data.getCurrentConfiguration().getApi_key() != null) {
			input_clientSecret.setText(Data.getCurrentConfiguration().getApi_key());
		}
		if (Data.getCurrentConfiguration().getProject_id() != null) {
			input_projectId.setText(Data.getCurrentConfiguration().getProject_id());
		}
	}

	@SuppressWarnings("deprecation")
    public void writeToConfigJson(String key, String value) throws IOException, URISyntaxException {

        //create client_secret.json file outside jar directory
        String absolutePath = new File("").getAbsolutePath();
        System.out.println(absolutePath);
        File jsonFile = new File(absolutePath + "/client_secret.json");

        if (jsonFile.createNewFile()) {
            FileUtils.copyInputStreamToFile(getClass().getClassLoader().getResourceAsStream("client_secretTemplate.json"), jsonFile);
            System.out.println("File created: " + jsonFile.getName());
        }

        JsonElement jelement = JsonParser.parseString(FileUtils.readFileToString(jsonFile));
        JsonObject jobject = jelement.getAsJsonObject();
        ((JsonObject) jobject.get("installed")).addProperty(key, value);

        Gson gson = new Gson();
        String resultingJson = gson.toJson(jelement);

        Files.write(resultingJson, jsonFile, Charsets.UTF_8);
        System.out.println("Zapisano " + value + " do " + key);


    }


    public boolean validateData(String clientId, String clientSecret, String projectId) {
        return clientId != null && clientSecret != null && projectId != null;
    }


}
