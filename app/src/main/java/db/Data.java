package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import configuration.Configuration;
import gui.controllers.loadingController;

//klasa wczytująca i modyfikująca zserializowane dane
public abstract class Data {

	static loadingController loadingController;
	static Configuration currentConfiguration = new Configuration();
	
	
	
	public static void initializeData() throws ClassNotFoundException, IOException {

		String absolutePath = new File("").getAbsolutePath();
		File file = new File(absolutePath + "/current.properties");
		System.out.println(absolutePath + "/current.properties");
		if(file.createNewFile()) {
			
			saveToFile("/current.properties", currentConfiguration);
			System.out.println("Created current.properties file");

		} else {
			System.out.println("Detected current.properties file");
		}
		System.out.println(readFromFile("current.properties").toString());
		currentConfiguration = readFromFile("current.properties");

	}
	
	public static void saveData() throws ClassNotFoundException, IOException {
		saveToFile("current.properties", currentConfiguration);
	}
	
	public static <E> boolean saveToFile(String filename, E o) throws ClassNotFoundException, IOException{
		
		String absolutePath = new File("").getAbsolutePath();
		
		FileOutputStream os = new FileOutputStream(absolutePath + "/" + filename);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		oos.writeObject(o);
		return true;
		
	}
	
	
	public static <E> E readFromFile(String filename) throws IOException, ClassNotFoundException{
		
		String absolutePath = new File("").getAbsolutePath();
		
		FileInputStream in = new FileInputStream(new File(absolutePath + "/current.properties"));
		System.out.println(absolutePath + "\\" + filename);
		ObjectInputStream oin = new ObjectInputStream(in);
		
		E result = (E) oin.readObject();

		return result;
	}


	public static Configuration getCurrentConfiguration() {
		return currentConfiguration;
	}

	public static void setCurrentConfiguration(Configuration currentConfiguration) {
		Data.currentConfiguration = currentConfiguration;
	}

	public static loadingController getLoadingController() {
		return loadingController;
	}

	public static void setLoadingController(loadingController loadingController) {
		Data.loadingController = loadingController;
	}
}
