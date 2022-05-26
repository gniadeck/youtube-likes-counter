package configuration;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

//klasa przechowująca obiekty konfiguracji
@Data
public class Configuration implements Serializable {
	
	public static final long serialVersionUID = 120491098;
	
	String api_key;
	LocalDate begining_date;
	String channel_id;
	String project_id;
	String client_id;
	String header_text;
	String middle_text;
	String przelicznik;

}
