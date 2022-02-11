package main;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import db.Data;
import gui.Launcher;



public class Main {
	
    @SuppressWarnings("deprecation")
	public static void main(String[] args)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException, ClassNotFoundException {
    	
    	Data.initializeData();
    	Launcher.main(args);
    	
    	
    }
}