package main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import configuration.Configuration;
import db.Data;
import gui.Animations;
import gui.controllers.MainScreenController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class YTConnector extends Task {

	MainScreenController MainScreenController;
	

	@Override
	protected Object call() {
		
		int likesChangeAmount;
		double moneyChangeAmount;
		boolean initialization = true; 

	        
		//inicjalizacja usługi youtube

        YouTube youtubeService;
		try {
			youtubeService = Library.getService();

  
		try {
			


	        //stwórz i wyślij żądanienie informacji o kanale użytkownika
	        YouTube.Channels.List channelsListByUsernameRequest = youtubeService.channels().list("snippet,statistics, contentDetails");
	        channelsListByUsernameRequest.setId(Data.getCurrentConfiguration().getChannel_id());
	        ChannelListResponse response = channelsListByUsernameRequest.execute();
	        Channel channel = response.getItems().get(0);
	        logText("Znaleziono kanał " + channel.getSnippet().getTitle());
	        setProgress(10);
	        

	        //uzyskiwanie danych dotyczących listy uploadów
	        String uploadsPlaylist = channel.getContentDetails().getRelatedPlaylists().getUploads();
	        
	        YouTube.PlaylistItems.List playlistItemRequest =
	                youtubeService.playlistItems().list("id,contentDetails,snippet");
	        playlistItemRequest.setPlaylistId(uploadsPlaylist);
	        
	        
	        playlistItemRequest.setFields(
	                "items(contentDetails/videoId,snippet/title,snippet/publishedAt,snippet/resourceId/videoId),nextPageToken,pageInfo");

	        String nextToken = "";
	        
	        List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
	        
	        List<String> videosIDs = new ArrayList<String>();
	        
	        LocalDate datapoczatkowa = Data.getCurrentConfiguration().getBegining_date();
	        DateTime dataPublikacji;
	        LocalDate data;
	        Date dataPublikacjiDate;

	        
	        //pobieranie spełniających kryteria filmów
	        logText("Pobieranie listy wrzuconych, przed zadaną datą, filmów... ");
	        int temp = 0;
	        do {
	            playlistItemRequest.setPageToken(nextToken);
	            PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

	            playlistItemList.addAll(playlistItemResult.getItems());

	            nextToken = playlistItemResult.getNextPageToken();
	           System.out.append(".");
	           System.out.println(playlistItemList.get(temp).getSnippet().getPublishedAt());
	           
	           if(Instant.ofEpochMilli(playlistItemList.get(temp).getSnippet().getPublishedAt().getValue()).atZone(ZoneId.systemDefault()).toLocalDate()
	        		   .isBefore(datapoczatkowa)) {
	        	   
	        	   break;
	        	   
	           }
	           temp = temp + playlistItemResult.getItems().size();
	           
	           
	        } while (nextToken != null);
	        
	        setProgress(40);

	        
	        
	        
	        //tworzenie kolekcji filmów spełniających waunki
	        for(int i = 0 ; i < playlistItemList.size(); i ++) {
	        	
	        	//znajdz date publikacji analizowanego filmu, TODO optymalizacja
	        	
	        	dataPublikacji = playlistItemList.get(i).getSnippet().getPublishedAt();
	        	dataPublikacjiDate = new Date(dataPublikacji.getValue());
	        	data = Instant.ofEpochMilli(dataPublikacji.getValue()).atZone(ZoneId.systemDefault()).toLocalDate();
	        	
	        	if(data.isAfter(datapoczatkowa)) {
	        		logText(playlistItemList.get(i).getSnippet().getTitle() + " wrzucony " + data);
	        		
	        		System.out.println("Dodaje do listy: " + playlistItemList.get(i).getSnippet().getTitle());
	            	videosIDs.add(playlistItemList.get(i).getSnippet().getResourceId().getVideoId());
	            	
	        	}

	        	
	        	
	        }
	        setProgress(60);
	        //swego rodzaju error handler w wypadku braku video które można zliczyć
	        if(videosIDs.size() == 0) {
	        	
	        	throwErrorAndGetBackToMainScreen("Nie znaleziono pasujących filmów. Spróbuj zmienić datę");
	        	
	        }

	        //stworzenie stringa zawierajacego ID filmów po przecinku, potrzebne do wysłania jednego requesta, zamiast kilkunastu
	        ArrayList<StringBuilder> stringRequests = new ArrayList<StringBuilder>();

	        
	        ArrayList<VideoListResponse> listaRequestow = new ArrayList<VideoListResponse>();
	        System.out.println(Double.valueOf(videosIDs.size()/49));
	        int numberOfRequests =  (int) Math.ceil(Double.valueOf(videosIDs.size())/49);
	        
	        logText("Liczba zaptyań: " + numberOfRequests);
	        logText("Pobrano: " + videosIDs.size());
	        
	        int remainingRequests = videosIDs.size();
	        int videosIDsCounter = 0;
	        int stringBuilderCounter = 0;
	        
	        logText("Tworzenie requesta...");
	        while(remainingRequests != 0) {
	        	
	        	stringRequests.add(new StringBuilder());
	        	
	        	int currentRequestSize;
	        	
	        	if(remainingRequests%49 != 0) {
	        		
	        		currentRequestSize = remainingRequests%49;
	        	} else {
	        		currentRequestSize = 49;
	        	}
	        	
	        	
	        	for(int i = 0; i < currentRequestSize; i++) {	        	
		        	if(i == (1-videosIDs.size())) {
		        	stringRequests.get(stringBuilderCounter).append(videosIDs.get(videosIDsCounter));
		        	} else {
		        	stringRequests.get(stringBuilderCounter).append(videosIDs.get(videosIDsCounter));
		        	stringRequests.get(stringBuilderCounter).append(",");
		        	}
		        	videosIDsCounter++;
	        }
	        	
	        	remainingRequests = remainingRequests - currentRequestSize;
	        	
//	        	System.out.println("Request o wielkości: " + currentRequestSize);
//	        	System.out.println("Treść requesta: " + stringRequests.get(stringBuilderCounter));
	        	
	        	stringBuilderCounter++;
	        	
	    
	        	
	        	
	        }
	        
	        logText("Utworzono requesty.");
	        
	           
	      //lista video, w której przechowywane będą wszystkie obiekty typu video
	        List<Video> videosList = new ArrayList<Video>();
	        
	        ArrayList<VideoListResponse> APIResponses = new ArrayList<VideoListResponse>();
	        

	        for(int i = 0; i < stringRequests.size(); i++) {
	        	
	        	APIResponses.add(youtubeService.videos().list("id, statistics,snippet")
	        			.setId(stringRequests.get(i).toString()).execute());
	        	videosList.addAll(APIResponses.get(i).getItems());
	        	logText("Wykonano request " + i + " z " + stringRequests.size());
	        	
	        }
	     
	        
	        logText("Pobrano " + videosList.size() + " filmów");
	        setProgress(80);
	        
	        logText(videosList.get(0).getSnippet().getTitle());
	        Double kwota = 0.0;
	        BigInteger likesAmount;
	        
	        
	        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
	        otherSymbols.setDecimalSeparator('.');
	        DecimalFormat format = new DecimalFormat("0.0#" , otherSymbols);
	        
	        int liczbaLapekWGore = 0;
	        
	        
	        while(true) {
	        	

	        	
	        for(int i = 0; i < videosList.size(); i++) {
	        	
	        	logText("Tytuł wideo: " + videosList.get(i).getSnippet().getTitle());
	        	logText("Liczba lapek w góre: " + videosList.get(i).getStatistics().getLikeCount());
	        	likesAmount = videosList.get(i).getStatistics().getLikeCount();
	        	liczbaLapekWGore = liczbaLapekWGore + likesAmount.intValue();
	        	kwota = Double.valueOf(format.format(liczbaLapekWGore * 0.01));
//	        	
	        }
	        
	        
	        logText("-----------------------");
	        logText("Wynik:");
	        logText("Liczba filmow: " + videosList.size());
	        logText("Liczba lapek w góre w sumie: " + liczbaLapekWGore);
	        logText("Zebrana kwota: " + format.format(Double.valueOf(BigDecimal.valueOf(liczbaLapekWGore).multiply(
	        		BigDecimal.valueOf(Double.valueOf(Data.getCurrentConfiguration().getPrzelicznik()))).toString())) + " zł");
	        

	        logText("Ustawiono widok.");
	        setProgress(100);
	        
	        try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        String currentPlnValue = MainScreenController.getPlnSum().getText().replaceFirst(" zł", "");
	        System.out.println(currentPlnValue);
	        Double currentPlnValueDouble = Double.valueOf(currentPlnValue);
	        try {
	        	System.out.println("Wartość: " + Double.valueOf(currentPlnValue));
	        	currentPlnValueDouble = Double.valueOf(currentPlnValue);
	        } catch (NumberFormatException e) {
	        	System.out.println("Błąd");
	        	
	        }
	        
	        System.out.println(format.format(liczbaLapekWGore*Double.valueOf(Data.getCurrentConfiguration().getPrzelicznik())));
	        System.out.println(MainScreenController.getThumbsUpSum().getText().replaceFirst(" łapek!", ""));
	        int currentThumbsUpNumber = (int) Integer.valueOf(MainScreenController.getThumbsUpSum().getText().replaceFirst(" łapek!", "")) ;
	        
	        //TODO zamiana na bigdecimal
	        if(initialization == true) {
	        	MainScreenController.setPlnSum(Double.valueOf(format.format(liczbaLapekWGore * Double.valueOf(Data.getCurrentConfiguration().getPrzelicznik()))).toString() + " zł");
	        	MainScreenController.setThumbsUpSum(liczbaLapekWGore + " łapek!");
	        } else {
	        	if(currentPlnValueDouble != null) {

	        		Animations.animateUpPLN(currentPlnValueDouble, Double.valueOf(BigDecimal.valueOf(liczbaLapekWGore)
	        				.multiply(BigDecimal.valueOf(Double.valueOf(Data.getCurrentConfiguration().getPrzelicznik()))).toString()));
	        		 Animations.animateUpThumbsUp(currentThumbsUpNumber, liczbaLapekWGore);
	        		
	        	}
	        }
	        

			
			Thread.sleep(10000);
			
			logText("Wykonuje request ponownie...");
			setProgress(0);
			liczbaLapekWGore = 0;
			initialization = false;
			APIResponses.clear();
			videosList.clear();
			
	        for(int i = 0; i < stringRequests.size(); i++) {
	        	
	        	APIResponses.add(youtubeService.videos().list("id, statistics,snippet")
	        			.setId(stringRequests.get(i).toString()).execute());
	        	videosList.addAll(APIResponses.get(i).getItems());
	        	
	        }
	        }
	        
		
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return youtubeService;
    	
		} catch (GeneralSecurityException | IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("IOException");
			e1.printStackTrace();
		}

		return null;
		
	}
	
	
	public MainScreenController getMainScreenController() {
		return MainScreenController;
	}

	public void setMainScreenController(MainScreenController mainScreenController) {
		MainScreenController = mainScreenController;
	}
	
	public void throwErrorAndGetBackToMainScreen(String message) {
		
    	Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				try {
					MainScreenController.throwErrorAndGetBackToMainScreen(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
    		
    	});
	}
	
	public void logText(String text) {
		
		db.Data.getLoading().getText_area().appendText(LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)) + ": " + text + "\n");
		
	}
	
	public void setProgress(double num) {
		db.Data.getLoading().getProgressBar().setProgress(num);
	}

}
