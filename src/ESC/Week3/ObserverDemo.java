package ESC.Week3;

/**
 * Created by HanWei on 9/2/2017.
 */
public class ObserverDemo {
//    public static void main(String[] args) {
//        StockGrabber grabber = new StockGrabber();
//        StockGrabber grabber1 = new StockGrabber();
//
//        StockObserver Observer1 = new StockObserver("Tenzin", grabber);
//        Observer1.watchStock("ibm");
//
//        StockObserver Observer2 = new StockObserver("Meow", grabber1);
//        grabber.updateWatchingAdd(Observer2, "aapl");
//
//        grabber.setPrice("ibm", 50);
//        grabber.setPrice("aapl", 0);
//
//
//        grabber.unregister(Observer1);
//    }

    public static void main(String[] args) {
        // Create the Subject object
        // It will handle updating all observers
        // as well as deleting and adding them
        StockGrabber stockGrabber = new StockGrabber();

        // Create an Observer that will be sent updates from Subject
        StockObserver observer1 = new StockObserver("Tenzin", stockGrabber);

        observer1.watchStock("ibm");

        stockGrabber.setPrice("ibm", 197.00);
        stockGrabber.setPrice("aapl", 677.60);
        stockGrabber.setPrice("goog", 676.40);


        StockObserver observer2 = new StockObserver("Meow", stockGrabber);
        observer2.watchStock("aapl");
        observer2.watchStock("goog");

        stockGrabber.setPrice("ibm", 197.00);
        stockGrabber.setPrice("aapl", 677.60);
        stockGrabber.setPrice("goog", 676.40);

        stockGrabber.unregister(observer2);

        stockGrabber.setPrice("ibm", 197.00);
        stockGrabber.setPrice("aapl", 677.60);
        stockGrabber.setPrice("goog", 676.40);
    }

}
