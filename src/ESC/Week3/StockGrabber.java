package ESC.Week3;

import java.util.ArrayList;
import java.util.HashMap;

//Uses the Subject interface to update all Observers

public class StockGrabber implements iSubject {

    private HashMap<Observer, ArrayList<String>> observers;
    private HashMap<String, Double> stocks;


//    private double ibmPrice;
//    private double aaplPrice;
//    private double googPrice;

    public StockGrabber() {

        // Creates an ArrayList to hold all observers

        observers = new HashMap<>();

        // Creates a HashMap to hold all stock

        stocks = new HashMap<>();
        stocks.put("ibm", 0.4);
        stocks.put("aapl", 0.01);
        stocks.put("goog", 10000.0);
    }

    public void register(Observer newObserver) {

        // Adds a new observer to the ArrayList

        observers.put(newObserver, new ArrayList<>());

    }

    public void unregister(Observer deleteObserver) {

        // Print out message

        System.out.println("Observer " + ((StockObserver) deleteObserver).getName() + " deleted");

        // Removes observer from the HashMap

        observers.remove(deleteObserver);
    }

    // Update the ArrayList containing the stocks being watched by an observer
    // OK I know this is slightly dangerous, especially if anyone can mess with the grabber's
    // list of updates. But I rly dunno wad else to do lol.

    public void updateWatchingAdd(Observer observer, String stockName) {
        if (observers.containsKey(observer)) {
            if (!observers.get(observer).contains(stockName)) {
                observers.get(observer).add(stockName);
            } else {
                System.out.println("You are already watching this stock!");
            }
        } else {
            System.out.println("You are not currently subscribed to me!");
        }
    }

    public void updateWatchingDelete(Observer observer, String stockName) {
        if (observers.containsKey(observer)) {
            if (observers.get(observer).contains(stockName)) {
                observers.get(observer).remove(stockName);
            } else {
                System.out.println("You are not watching this stock.");
            }
        } else {
            System.out.println("You are not currently subscribed to me!");
        }
    }

    public void notifyObserver(String stockName) {

        // Cycle through all observers and notifies them of
        // price changes

        for (Observer observer : observers.keySet()) {
            if (observers.get(observer).contains(stockName)) {
                observer.update(stockName);
            }
        }
    }

    // Get the price of a particular stock

    public double getStock(String stockName) {
        try {
            double price = stocks.get(stockName);
            return price;
        } catch (Exception e) {
            System.out.println("No such stock exists.");
            return -1;
        }
    }

    // Add a new stock to the grabber's list

    public void addStock(String stockName, double value) {
        stocks.put(stockName, value);
    }

    public void removeStock(String stockName) {
        if (stocks.containsKey(stockName)) {
            stocks.remove(stockName);
            for (Observer observer :
                    observers.keySet()) {
                if (observers.get(observer).contains(stockName)) {
                    System.out.println("Removing stock for Observer " + ((StockObserver) observer).getObserverID() + ": " + ((StockObserver) observer).getName());
                    observers.get(observer).remove(stockName);
                    ((StockObserver) observer).removeFromWatched(stockName);
                }
            }
        } else {
            System.out.println("No such stock exists.");
        }
    }

    // Change prices for all stockNames and notifies observers of changes

    public void setPrice(String stockName, double newPrice) {

        if (stocks.containsKey(stockName)) {
            stocks.replace(stockName, newPrice);
            notifyObserver(stockName);
        } else {
            System.out.println("That stock does not exist.");
        }
    }
}
