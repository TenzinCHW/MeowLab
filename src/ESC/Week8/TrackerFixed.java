package ESC.Week8;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HanWei on 16/3/2017.
 */

class test2 extends Thread {
    TrackerFixed tracker;

    public test2(TrackerFixed tra) {
        this.tracker = tra;
    }

    public void run() {
        TrackerFixed.MutablePointFixed loc = tracker.getLocation("somestring");
//        loc.x = -1212000;
    }
}

public class TrackerFixed {
    //@guarded by this
    private final Map<String, MutablePointFixed> locations;

    public TrackerFixed(Map<String, MutablePointFixed> locations) {
        this.locations = new HashMap<>();
        for (String place :
                locations.keySet()) {
            this.locations.put(place, locations.get(place));
        }
    }

    public Map<String, MutablePointFixed> getLocations() {
        HashMap<String, MutablePointFixed> places = new HashMap<>();
        for (String place :
                locations.keySet()) {
            places.put(place, locations.get(place));
        }
        return places;
    }

    public MutablePointFixed getLocation(String id) {
        MutablePointFixed loc = new MutablePointFixed(locations.get(id));
        return loc;
    }

    public void setLocation(String id, int x, int y) {
        MutablePointFixed loc = locations.get(id);

        if (loc == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }

        locations.remove(loc);
        locations.put(id, new MutablePointFixed(x, y));
    }

    //this class is not thread-safe (why?) and keep it unmodified.
    class MutablePointFixed {
        private final int x, y;

        public MutablePointFixed(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public MutablePointFixed(MutablePointFixed p) {
            this.x = p.x;
            this.y = p.y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
