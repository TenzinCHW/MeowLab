package ESC.Week11;

import java.util.HashSet;
import java.util.Set;

public class DLExample {

}

class Taxi {
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return location;
    }

    public synchronized void setLocation(Point location) {  // If Thread 1 runs this at time = 0
        this.location = location;
        if (location.equals(destination))
            dispatcher.notifyAvailable(this);   // Thread 1 gets here at time = 1 and tries to acquire lock on Dispatcher -> deadlock
    }

    public synchronized Point getDestination() {
        return destination;
    }
}

class Dispatcher {
    private final Set<Taxi> taxis;
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<Taxi>();
        availableTaxis = new HashSet<Taxi>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {  // Thread 2 runs this at time = 0
        Image image = new Image();
        for (Taxi t : taxis)
            image.drawMarker(t.getLocation());  // Thread 2 gets here at time = 1 and tries to acquire lock on Taxi -> deadlock
        return image;
    }
}

class Image {
    public void drawMarker(Point p) {
    }
}

class Point {

}

