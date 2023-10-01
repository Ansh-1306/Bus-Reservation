import java.util.ArrayList;
import java.util.List;

class BusStop {
    String name;

    public BusStop(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
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
        s = s + route.get(route.size() - 1) + "]\n";
        return s;
    }

    // public void simulateTravel() {
    //     while (currentStopIndex < route.size() - 1) {
    //         BusStop currentStop = route.get(currentStopIndex);
    //         BusStop nextStop = route.get(currentStopIndex + 1);

    //         int distanceToNextStop = nextStop.distance - currentStop.distance;
    //         double travelTime = distanceToNextStop / speed; // Travel time in hours

    //         System.out.println("Bus: " + name);
    //         System.out.println("Current Stop: " + currentStop.name);
    //         System.out.println("Next Stop: " + nextStop.name);
    //         System.out.println("Estimated Travel Time: " + travelTime + " hours");

    //         try {
    //             Thread.sleep((long) (travelTime * 3600)); // Convert hours to milliseconds
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }

    //         currentStopIndex++;
    // //     }

    //     System.out.println("Bus " + name + " has reached the destinatio");
    // }
}