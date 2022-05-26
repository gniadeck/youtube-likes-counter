package gui;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import gui.controllers.MainScreenController;

public class Animations {
	
	static MainScreenController MainScreenController;
	
	public static void animateUpPLN(double first, double last) throws InterruptedException {
		
		BigDecimal firstBD = BigDecimal.valueOf(first);
		BigDecimal lastBD = BigDecimal.valueOf(last);
		BigDecimal differenceBD =firstBD.subtract(lastBD).abs();
		long waitingtime = 1;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("0.00" , otherSymbols);
		
        if(firstBD.compareTo(lastBD) < 0) {
        	
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

		int difference = Math.abs(first-last);
		long waitingtime = 1;
		
		if(first<last) {
			for(int i = 0; i < difference; i++) {
				
				first++;
				MainScreenController.setThumbsUpSum(first + " łapek!");;
				System.out.println(first);
				
				Thread.sleep(50);

			}
		} else {
			MainScreenController.setThumbsUpSum(String.valueOf(last) + " łapek!");
		}
	}

	public static MainScreenController getMainScreenController() {
		return MainScreenController;
	}

	public static void setMainScreenController(MainScreenController mainScreenController) {
		MainScreenController = mainScreenController;
	}
}
