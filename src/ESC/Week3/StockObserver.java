package ESC.Week3;

//Represents each Observer that is monitoring changes in the subject

import java.util.HashMap;

public class StockObserver implements Observer {
	
//	private double ibmPrice;
//	private double aaplPrice;
//	private double googPrice;
	private HashMap<String, Double> watchedStocks;
	
	// Static used as a counter
	
	private static int observerIDTracker = 0;
	
	// Used to track the observers
	
	private int observerID;
	
	// Will hold reference to the StockGrabber object
	
	private iSubject stockGrabber;
	
	public StockObserver(iSubject stockGrabber){
		
		// Store the reference to the stockGrabber object so
		// I can make calls to its methods
		
		this.stockGrabber = stockGrabber;
		
		// Assign an observer ID and increment the static counter
		
		this.observerID = ++observerIDTracker;
		
		// Message notifies user of new observer
		
		System.out.println("New Observer " + this.observerID);
		
		// Add the observer to the Subjects ArrayList
		
		stockGrabber.register(this);
		
	}

	// Add a stock to watched stocks HashMap

	public void watchStock(String stockName) {
        if (!((StockGrabber)stockGrabber).getStocks().containsKey(stockName)) {
            System.out.println("No such stock exists.");
        } else if (watchedStocks.containsKey(stockName)) {
            System.out.println("You are already watching this stock.");
        } else {
            watchedStocks.put(stockName, ((StockGrabber)stockGrabber).getStock(stockName));
        }
    }

	// Called to update all observers
	
	public void update() {

        for (String stockName :
                watchedStocks.keySet()) {
            watchedStocks.replace(stockName, ((StockGrabber) stockGrabber).getStock(stockName));
        }

		printThePrices();
		
	}
	
	public void printThePrices(){

        for (String stockName :
                watchedStocks.keySet()) {
            System.out.println(stockName + ": " + watchedStocks.get(stockName));
        }
		
	}
	
}
