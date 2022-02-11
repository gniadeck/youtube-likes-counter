package configuration;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

//klasa przechowująca obiekty konfiguracji
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
	
	
	public Configuration(String api_key, LocalDate begining_date, String channel_id, String project_id, String client_id, String przelicznik) {
		super();
		this.api_key = api_key;
		this.begining_date = begining_date;
		this.channel_id = channel_id;
		this.project_id = project_id;
		this.client_id = client_id;
		this.przelicznik = przelicznik;
	}
	
	public Configuration() {
	}
	

	public String getPrzelicznik() {
		return przelicznik;
	}

	public void setPrzelicznik(String przelicznik) {
		this.przelicznik = przelicznik;
	}

	@Override
	public int hashCode() {
		return Objects.hash(api_key, begining_date, channel_id, client_id, project_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		return Objects.equals(api_key, other.api_key) && Objects.equals(begining_date, other.begining_date)
				&& Objects.equals(channel_id, other.channel_id) && Objects.equals(client_id, other.client_id)
				&& Objects.equals(project_id, other.project_id);
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public LocalDate getBegining_date() {
		return begining_date;
	}
	public void setBegining_date(LocalDate begining_date) {
		this.begining_date = begining_date;
	}

	

	@Override
	public String toString() {
		return "Configuration [api_key=" + api_key + ", begining_date=" + begining_date + ", channel_id=" + channel_id
				+ ", project_id=" + project_id + ", client_id=" + client_id + ", header_text=" + header_text
				+ ", middle_text=" + middle_text + "]";
	}

	public String getHeader_text() {
		return header_text;
	}

	public void setHeader_text(String header_text) {
		this.header_text = header_text;
	}

	public String getMiddle_text() {
		return middle_text;
	}

	public void setMiddle_text(String middle_text) {
		this.middle_text = middle_text;
	}

	
	
	
	
	
	

}
