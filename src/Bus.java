import java.util.ArrayList;
import java.util.List;

class BusStop {
    String name;
    int distance; // Distance from source (in km)

    public BusStop(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return name ;
    }
    
}

class Bus {
    String name;
    String type;
    List<BusStop> route;
    int currentStopIndex;
    double speed; // Speed in km/h

    public Bus(String name, String type, List<BusStop> route, double speed) {
        this.name = name;
        this.type = type;
        this.route = route;
        this.speed = speed;
        this.currentStopIndex = 0;
    }

    @Override
    public String toString() {
        String s = "\nBus Name : " + name + "\nBus Type : " + type + "\n[";
        for (int i = 0; i < (route.size() - 1); i++) {
            s = s + route.get(i) + " --> ";
        }
        s = s + route.get(route.size()-1) + "]\n";
        return s;
    }

    public void simulateTravel() {
        while (currentStopIndex < route.size() - 1) {
            BusStop currentStop = route.get(currentStopIndex);
            BusStop nextStop = route.get(currentStopIndex + 1);

            int distanceToNextStop = nextStop.distance - currentStop.distance;
            double travelTime = distanceToNextStop / speed; // Travel time in hours

            System.out.println("Bus: " + name);
            System.out.println("Current Stop: " + currentStop.name);
            System.out.println("Next Stop: " + nextStop.name);
            System.out.println("Estimated Travel Time: " + travelTime + " hours");

            try {
                Thread.sleep((long) (travelTime * 3600)); // Convert hours to milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentStopIndex++;
        }

        System.out.println("Bus " + name + " has reached the destinatio/0n nbvz r cvbnm,./1.");
    }
}

class MultiBusTrackingSimulation {
    public static void main(String[] args) {
        List<BusStop> route1 = new ArrayList<>();
        route1.add(new BusStop("Source", 0));
        route1.add(new BusStop("StopA1", 15));
        route1.add(new BusStop("StopA2", 30));
        route1.add(new BusStop("DestinationA", 45));

        List<BusStop> route2 = new ArrayList<>();
        route2.add(new BusStop("Source", 0));
        route2.add(new BusStop("StopB1", 10));
        route2.add(new BusStop("StopB2", 20));
        route2.add(new BusStop("StopB3", 30));
        route2.add(new BusStop("DestinationB", 40));

        Bus bus1 = new Bus("Express 123", "Express", route1, 60); // 60 km/h
        Bus bus2 = new Bus("Local 456", "Local", route2, 40); // 40 km/h

        System.out.println("Simulating Bus Tracking");
        System.out.println("-----------------------");

        Thread thread1 = new Thread(bus1::simulateTravel);
        Thread thread2 = new Thread(bus2::simulateTravel);

        System.out.println(bus1);
        System.out.println(bus2);
        

        thread1.start();
        thread2.start();
    }
}
