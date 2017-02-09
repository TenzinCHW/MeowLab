package ESC.Week3;

//Represents each Observer that is monitoring changes in the subject

import java.util.HashMap;

public class StockObserver implements Observer {

//	private double ibmPrice;
//	private double aaplPrice;
//	private double googPrice;

    // Name of Observer

    private String name;

    // Map of all currently watched stocks and their prices

    private HashMap<String, Double> watchedStocks;

    // Static used as a counter

    private static int observerIDTracker = 0;

    // Used to track the observers

    private int observerID;

    // Will hold reference to the StockGrabber object

    private iSubject stockGrabber;

    public StockObserver(String name, iSubject stockGrabber) {
        this.name = name;

        // Store the reference to the stockGrabber object so
        // I can make calls to its methods

        this.stockGrabber = stockGrabber;

        // Assign an observer ID and increment the static counter

        this.observerID = ++observerIDTracker;

        // Create HashMap

        watchedStocks = new HashMap<>();

        // Message notifies user of new observer

        System.out.println("New Observer " + this.observerID + ": " + name);

        // Add the observer to the Subjects ArrayList

        stockGrabber.register(this);

    }

    public String getName() {
        return name;
    }

    public int getObserverID() {
        return observerID;
    }

    // Add a stock to watched stocks HashMap

    public void watchStock(String stockName) {
        double price = ((StockGrabber) stockGrabber).getStock(stockName);
        if (price < 0) {
            System.out.println("No such stock exists.");
        } else if (watchedStocks.containsKey(stockName)) {
            System.out.println("You are already watching this stock.");
        } else {
            watchedStocks.put(stockName, price);
            ((StockGrabber) stockGrabber).updateWatchingAdd(this, stockName);
        }
    }

    // Remove a currently watched stock

    public void removeFromWatched(String stockName) {
        if (watchedStocks.containsKey(stockName)) {
            watchedStocks.remove(stockName);
            ((StockGrabber)stockGrabber).updateWatchingDelete(this, stockName);
        } else {
            System.out.println("You're not currently watching this stock!");
        }
    }

    // Called to update all observers

    public void update(String stockName) {

        watchedStocks.replace(stockName, ((StockGrabber) stockGrabber).getStock(stockName));

        printThePrices();

    }

    public void printThePrices() {

        for (String stockName :
                watchedStocks.keySet()) {
            System.out.println(stockName + ": " + watchedStocks.get(stockName));
        }

    }

}
