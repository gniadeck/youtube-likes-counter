package gui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import gui.controllers.MainScreenController;

public class Animations {
	
	static MainScreenController MainScreenController;
	
	public static void animateUpPLN(double first, double last) throws InterruptedException {
		
		BigDecimal firstBD = BigDecimal.valueOf(first);
		BigDecimal lastBD = BigDecimal.valueOf(last);
		double difference = Math.abs(Double.valueOf(firstBD.subtract(lastBD).toString()));
		BigDecimal differenceBD = BigDecimal.valueOf(difference);
		long waitingtime = 1;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("0.00" , otherSymbols);
		
        if(firstBD.compareTo(lastBD) == -1) {
        	
        	for(int i = 0; i < differenceBD.multiply(BigDecimal.valueOf(100)).intValue(); i++) {
        		firstBD = firstBD.add(BigDecimal.valueOf(0.01));
        		System.out.println("Ustawiam kwote na " + firstBD);
        		MainScreenController.setPlnSum(format.format(Double.valueOf(firstBD.toString())) + " zł");
        		Thread.sleep(50);
        	}
        	
        } else {
        	System.out.println("Roznica ujemna lub roznicy brak.");
        }
        
        

	}
	
	public static void animateUpThumbsUp(int first, int last) throws InterruptedException {
		BigInteger firstBD = BigInteger.valueOf(first);
		BigInteger lastBD = BigInteger.valueOf(last);
		double difference = Math.abs(first-last);
		long waitingtime = 1;
		
		if(first<last) {
			for(int i = 0; i < difference; i++) {
				
				first = BigInteger.valueOf(first).add(BigInteger.valueOf(1)).intValue();
				MainScreenController.setThumbsUpSum(first + " łapek!");;
				System.out.println(firstBD);
				
				Thread.sleep(50);

			}
		} else {
			MainScreenController.setThumbsUpSum(String.valueOf(last) + " łapek!");
		}
	}

	public MainScreenController getMainScreenController() {
		return MainScreenController;
	}

	public void setMainScreenController(MainScreenController mainScreenController) {
		MainScreenController = mainScreenController;
	}
	
	
	

}
