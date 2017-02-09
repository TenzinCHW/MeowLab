package ESC.Week3;

import java.util.ArrayList;
import java.util.HashMap;

//Uses the Subject interface to update all Observers

public class StockGrabber implements iSubject {

    private ArrayList<Observer> observers;
    private HashMap<String, Double> stocks;

//    private double ibmPrice;
//    private double aaplPrice;
//    private double googPrice;

    public StockGrabber() {

        // Creates an ArrayList to hold all observers

        observers = new ArrayList<Observer>();

        // Creates a HashMap to hold all stock

        stocks = new HashMap<>();
        stocks.put("ibm", 0.4);
        stocks.put("aapl", 0.01);
        stocks.put("goog", 10000.0);
    }

    public void register(Observer newObserver) {

        // Adds a new observer to the ArrayList

        observers.add(newObserver);

    }

    public void unregister(Observer deleteObserver) {

        // Get the index of the observer to delete

        int observerIndex = observers.indexOf(deleteObserver);

        // Print out message (Have to increment index to match)

        System.out.println("Observer " + (observerIndex + 1) + " deleted");

        // Removes observer from the ArrayList

        observers.remove(observerIndex);

    }

    public void notifyObserver() {

        // Cycle through all observers and notifies them of
        // price changes

        for (Observer observer : observers) {

            observer.update();

        }
    }

    public HashMap<String, Double> getStocks() {
        return stocks;
    }

    public double getStock(String stockName) {
        return stocks.get(stockName);
    }

    public void addStock(String stockName, double value) {
        stocks.put(stockName, value);
    }

    // Change prices for all stockNames and notifies observers of changes

    public void setPrice(String stockName, double newPrice) {

        stocks.replace(stockName, newPrice);

        notifyObserver();

    }

}
