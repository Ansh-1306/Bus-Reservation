import java.util.*;

public class Graph {
    public static final int numStops = 33;
    static Scanner sc = new Scanner(System.in);
    static BusStop[] busStops = new BusStop[numStops];
    static ArrayList<Edge>[] graph;
    static String[] stopNames = { "Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha", "Bharuch", "Bhavnagar",
            "Botad", "Chhota Udaipur", "Dahod", "Dang", "Devbhoomi Dwarka", "Gandhinagar", "Gir Somnath", "Jamnagar",
            "Junagadh", "Kheda", "Kutch", "Mahisagar", "Mehsana", "Morbi", "Narmada", "Navsari", "Panchmahal", "Patan",
            "Porbandar", "Rajkot", "Sabarkantha", "Surat", "Surendranagar", "Tapi", "Vadodara", "Valsad" };

    Graph() {
        graph = new ArrayList[numStops];
        initialiseRoutes(graph);
        for (int i = 0; i < numStops; i++) {
            busStops[i] = new BusStop(stopNames[i]);
        }
    }

    static class Edge {
        BusStop src;
        BusStop dst;
        int distance;

        private Edge(BusStop src, BusStop dst, int distance) {
            this.src = src;
            this.dst = dst;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Edge [src=" + src + ", dst=" + dst + ", distance=" + distance + "]";
        }

        private int getDistance() {
            return distance;
        }

    }

    public static void findShortestPath(ArrayList<Edge>[] graph, BusStop source, BusStop destination) {
        BusStop src = busStops[find_index(source)];
        BusStop dst = busStops[find_index(destination)];
        Map<BusStop, Integer> distance = new HashMap<>();
        Map<BusStop, BusStop> previous = new HashMap<>();
        Set<BusStop> visited = new HashSet<>();

        // Initialize the distance map with infinity for all nodes except the start node
        for (BusStop node : busStops) {
            distance.put(node, Integer.MAX_VALUE);
        }
        distance.put(src, 0);

        // Create a priority queue to keep track of nodes with minimum distance
        PriorityQueue<BusStop> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        queue.add(src);

        while (!queue.isEmpty()) {
            BusStop currentNode = queue.poll();
            visited.add(currentNode);

            int nodeIndex = find_index(currentNode);
            ArrayList<Edge> neighbours = graph[nodeIndex];

            for (Edge neighbourEdge : neighbours) {
                BusStop neighborNode = neighbourEdge.dst;
                int edgeWeight = neighbourEdge.distance;
                int newDistance = distance.get(currentNode) + edgeWeight;

                if (newDistance < distance.get(neighborNode)) {
                    distance.put(neighborNode, newDistance);
                    previous.put(neighborNode, currentNode);
                    if (!visited.contains(neighborNode)) {
                        queue.add(neighborNode);
                    }
                }
            }
        }
        if (dst == null) {
            System.out.println();
            for (BusStop stop : busStops) {
                System.out.printf(" %-20s == %10s km\n", stop, distance.get(stop));
            }
        } else {
            List<BusStop> shortestPath = new ArrayList<>();
            BusStop currentNode = dst;
            while (previous.containsKey(currentNode)) {
                shortestPath.add(currentNode);
                currentNode = previous.get(currentNode);
            }
            shortestPath.add(src);
            Collections.reverse(shortestPath);

            System.out.print("\nShortest Path from " + src + " to " + dst + ": ");
            for (BusStop s : shortestPath) {
                System.out.print(s + " --> ");
            }
            System.out.println(distance.get(dst) + " km");
        }
    }
    private static int find_index(BusStop stop) {
        for (int i = 0; i < numStops; i++) {
            if (busStops[i].name.equals(stop.name)) {
                return i;
            }
        }
        return -1;
    }

    public static void initialiseRoutes(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(busStops[0], busStops[2], 65));
        graph[0].add(new Edge(busStops[0], busStops[6], 170));
        graph[0].add(new Edge(busStops[0], busStops[7], 120));
        graph[0].add(new Edge(busStops[0], busStops[12], 30));
        graph[0].add(new Edge(busStops[0], busStops[16], 60));
        graph[0].add(new Edge(busStops[0], busStops[19], 75));
        graph[0].add(new Edge(busStops[0], busStops[29], 125));

        graph[1].add(new Edge(busStops[1], busStops[6], 70));
        graph[1].add(new Edge(busStops[1], busStops[7], 90));
        graph[1].add(new Edge(busStops[1], busStops[13], 100));
        graph[1].add(new Edge(busStops[1], busStops[15], 45));
        graph[1].add(new Edge(busStops[1], busStops[26], 75));

        graph[2].add(new Edge(busStops[2], busStops[0], 65));
        graph[2].add(new Edge(busStops[2], busStops[5], 50));
        graph[2].add(new Edge(busStops[2], busStops[16], 20));
        graph[2].add(new Edge(busStops[2], busStops[31], 45));

        graph[3].add(new Edge(busStops[3], busStops[12], 50));
        graph[3].add(new Edge(busStops[3], busStops[16], 120));
        graph[3].add(new Edge(busStops[3], busStops[18], 80));
        graph[3].add(new Edge(busStops[3], busStops[27], 50));

        graph[4].add(new Edge(busStops[4], busStops[17], 350));
        graph[4].add(new Edge(busStops[4], busStops[19], 100));
        graph[4].add(new Edge(busStops[4], busStops[24], 80));
        graph[4].add(new Edge(busStops[4], busStops[27], 150));

        graph[5].add(new Edge(busStops[5], busStops[2], 50));
        graph[5].add(new Edge(busStops[5], busStops[21], 50));
        graph[5].add(new Edge(busStops[5], busStops[28], 70));
        graph[5].add(new Edge(busStops[5], busStops[31], 70));

        graph[6].add(new Edge(busStops[6], busStops[0], 170));
        graph[6].add(new Edge(busStops[6], busStops[1], 70));
        graph[6].add(new Edge(busStops[6], busStops[7], 80));

        graph[7].add(new Edge(busStops[7], busStops[0], 120));
        graph[7].add(new Edge(busStops[7], busStops[1], 90));
        graph[7].add(new Edge(busStops[7], busStops[6], 80));
        graph[7].add(new Edge(busStops[7], busStops[26], 80));
        graph[7].add(new Edge(busStops[7], busStops[29], 70));

        graph[8].add(new Edge(busStops[8], busStops[9], 70));
        graph[8].add(new Edge(busStops[8], busStops[21], 90));
        graph[8].add(new Edge(busStops[8], busStops[23], 80));
        graph[8].add(new Edge(busStops[8], busStops[31], 100));

        graph[9].add(new Edge(busStops[9], busStops[8], 70));
        graph[9].add(new Edge(busStops[9], busStops[18], 90));
        graph[9].add(new Edge(busStops[9], busStops[23], 50));

        graph[10].add(new Edge(busStops[10], busStops[22], 80));
        graph[10].add(new Edge(busStops[10], busStops[30], 100));

        graph[11].add(new Edge(busStops[11], busStops[14], 135));
        graph[11].add(new Edge(busStops[11], busStops[25], 100));

        graph[12].add(new Edge(busStops[12], busStops[0], 30));
        graph[12].add(new Edge(busStops[12], busStops[3], 50));
        graph[12].add(new Edge(busStops[12], busStops[16], 30));
        graph[12].add(new Edge(busStops[12], busStops[19], 30));
        graph[12].add(new Edge(busStops[12], busStops[27], 30));

        graph[13].add(new Edge(busStops[13], busStops[1], 100));
        graph[13].add(new Edge(busStops[13], busStops[15], 60));

        graph[14].add(new Edge(busStops[14], busStops[11], 135));
        graph[14].add(new Edge(busStops[14], busStops[20], 100));
        graph[14].add(new Edge(busStops[14], busStops[25], 150));
        graph[14].add(new Edge(busStops[14], busStops[26], 90));

        graph[15].add(new Edge(busStops[15], busStops[1], 45));
        graph[15].add(new Edge(busStops[15], busStops[13], 60));
        graph[15].add(new Edge(busStops[15], busStops[25], 100));
        graph[15].add(new Edge(busStops[15], busStops[26], 100));

        graph[16].add(new Edge(busStops[16], busStops[0], 60));
        graph[16].add(new Edge(busStops[16], busStops[2], 20));
        graph[16].add(new Edge(busStops[16], busStops[3], 120));
        graph[16].add(new Edge(busStops[16], busStops[12], 30));
        graph[16].add(new Edge(busStops[16], busStops[18], 50));
        graph[16].add(new Edge(busStops[16], busStops[23], 70));
        graph[16].add(new Edge(busStops[16], busStops[31], 45));

        graph[17].add(new Edge(busStops[17], busStops[4], 350));
        graph[17].add(new Edge(busStops[17], busStops[20], 250));
        graph[17].add(new Edge(busStops[17], busStops[24], 235));
        graph[17].add(new Edge(busStops[17], busStops[29], 350));

        graph[18].add(new Edge(busStops[18], busStops[3], 80));
        graph[18].add(new Edge(busStops[18], busStops[9], 90));
        graph[18].add(new Edge(busStops[18], busStops[16], 50));
        graph[18].add(new Edge(busStops[18], busStops[23], 100));

        graph[19].add(new Edge(busStops[19], busStops[0], 75));
        graph[19].add(new Edge(busStops[19], busStops[4], 100));
        graph[19].add(new Edge(busStops[19], busStops[12], 30));
        graph[19].add(new Edge(busStops[19], busStops[24], 40));
        graph[19].add(new Edge(busStops[19], busStops[27], 50));
        graph[19].add(new Edge(busStops[19], busStops[29], 140));

        graph[20].add(new Edge(busStops[20], busStops[14], 100));
        graph[20].add(new Edge(busStops[20], busStops[17], 250));
        graph[20].add(new Edge(busStops[20], busStops[26], 70));
        graph[20].add(new Edge(busStops[20], busStops[29], 100));

        graph[21].add(new Edge(busStops[21], busStops[5], 50));
        graph[21].add(new Edge(busStops[21], busStops[8], 90));
        graph[21].add(new Edge(busStops[21], busStops[28], 90));
        graph[21].add(new Edge(busStops[21], busStops[30], 75));
        graph[21].add(new Edge(busStops[21], busStops[31], 90));

        graph[22].add(new Edge(busStops[22], busStops[10], 80));
        graph[22].add(new Edge(busStops[22], busStops[28], 30));
        graph[22].add(new Edge(busStops[22], busStops[30], 50));
        graph[22].add(new Edge(busStops[22], busStops[32], 35));

        graph[23].add(new Edge(busStops[23], busStops[8], 80));
        graph[23].add(new Edge(busStops[23], busStops[9], 50));
        graph[23].add(new Edge(busStops[23], busStops[16], 70));
        graph[23].add(new Edge(busStops[23], busStops[18], 100));
        graph[23].add(new Edge(busStops[23], busStops[31], 55));

        graph[24].add(new Edge(busStops[24], busStops[4], 80));
        graph[24].add(new Edge(busStops[24], busStops[17], 235));
        graph[24].add(new Edge(busStops[24], busStops[19], 40));
        graph[24].add(new Edge(busStops[24], busStops[29], 120));

        graph[25].add(new Edge(busStops[25], busStops[11], 100));
        graph[25].add(new Edge(busStops[25], busStops[14], 150));
        graph[25].add(new Edge(busStops[25], busStops[15], 100));
        graph[25].add(new Edge(busStops[25], busStops[26], 175));

        graph[26].add(new Edge(busStops[26], busStops[1], 75));
        graph[26].add(new Edge(busStops[26], busStops[7], 80));
        graph[26].add(new Edge(busStops[26], busStops[14], 90));
        graph[26].add(new Edge(busStops[26], busStops[15], 100));
        graph[26].add(new Edge(busStops[26], busStops[20], 70));
        graph[26].add(new Edge(busStops[26], busStops[25], 175));
        graph[26].add(new Edge(busStops[26], busStops[29], 110));

        graph[27].add(new Edge(busStops[27], busStops[3], 50));
        graph[27].add(new Edge(busStops[27], busStops[4], 150));
        graph[27].add(new Edge(busStops[27], busStops[12], 30));
        graph[27].add(new Edge(busStops[27], busStops[19], 50));

        graph[28].add(new Edge(busStops[28], busStops[5], 70));
        graph[28].add(new Edge(busStops[28], busStops[21], 90));
        graph[28].add(new Edge(busStops[28], busStops[22], 30));
        graph[28].add(new Edge(busStops[28], busStops[30], 75));

        graph[29].add(new Edge(busStops[29], busStops[0], 125));
        graph[29].add(new Edge(busStops[29], busStops[7], 70));
        graph[29].add(new Edge(busStops[29], busStops[17], 350));
        graph[29].add(new Edge(busStops[29], busStops[19], 140));
        graph[29].add(new Edge(busStops[29], busStops[20], 100));
        graph[29].add(new Edge(busStops[29], busStops[24], 120));
        graph[29].add(new Edge(busStops[29], busStops[26], 110));

        graph[30].add(new Edge(busStops[30], busStops[10], 100));
        graph[30].add(new Edge(busStops[30], busStops[21], 75));
        graph[30].add(new Edge(busStops[30], busStops[22], 50));
        graph[30].add(new Edge(busStops[30], busStops[28], 75));

        graph[31].add(new Edge(busStops[31], busStops[2], 45));
        graph[31].add(new Edge(busStops[31], busStops[5], 70));
        graph[31].add(new Edge(busStops[31], busStops[8], 100));
        graph[31].add(new Edge(busStops[31], busStops[16], 45));
        graph[31].add(new Edge(busStops[31], busStops[21], 90));
        graph[31].add(new Edge(busStops[31], busStops[23], 55));

        graph[32].add(new Edge(busStops[32], busStops[22], 35));

    }
}
// private static void displayStops(int delay) throws InterruptedException {
// System.out.println("+-------------------------+");
// System.out.println("| STOP LIST |");
// System.out.println("+-------------------------+\n");
// ArrayList<BusStop> stopNames = new ArrayList<>(Arrays.asList(busStops));
// int i = 1;
// for (BusStop st : stopNames) {
// Thread.sleep(delay);
// if (i < 10) {
// System.out.println("0" + i++ + " -> " + st);
// } else {
// System.out.println(i++ + " -> " + st);
// }
// }
// }

// private static void displaySorted(ArrayList<Edge> graph) throws
// InterruptedException {
// System.out.println();
// for (Edge e : graph) {
// Thread.sleep(100);
// System.out.printf("%-20s == %10s km\n", e.dst, e.distance);
// }
// }

// public static void findShortestPath(ArrayList<Edge>[] graph, String src,
// String dst) throws InterruptedException {
// Map<String, Integer> distance = new HashMap<>();
// Map<String, String> previous = new HashMap<>();
// Set<String> visited = new HashSet<>();

// // Initialize the distance map with infinity for all nodes except the start
// node
// for (String node : busStops) {
// distance.put(node, Integer.MAX_VALUE);
// }
// distance.put(src, 0);

// // Create a priority queue to keep track of nodes with minimum distance
// PriorityQueue<String> queue = new
// PriorityQueue<>(Comparator.comparingInt(distance::get));
// queue.add(src);

// while (!queue.isEmpty()) {
// String currentNode = queue.poll();
// visited.add(currentNode);

// int nodeIndex = find_index(currentNode);
// ArrayList<Edge> neighbours = graph[nodeIndex];

// for (Edge neighbourEdge : neighbours) {
// String neighborNode = neighbourEdge.dst;
// int edgeWeight = neighbourEdge.distance;
// int newDistance = distance.get(currentNode) + edgeWeight;

// if (newDistance < distance.get(neighborNode)) {
// distance.put(neighborNode, newDistance);
// previous.put(neighborNode, currentNode);
// if (!visited.contains(neighborNode)) {
// queue.add(neighborNode);
// }
// }
// }
// }
// if (dst == null) {
// System.out.println();
// for (String stop : busStops) {
// System.out.printf(" %-20s == %10s km\n", stop, distance.get(stop));
// Thread.sleep(100);
// }
// } else {
// List<String> shortestPath = new ArrayList<>();
// String currentNode = dst;
// while (previous.containsKey(currentNode)) {
// shortestPath.add(currentNode);
// currentNode = previous.get(currentNode);
// }
// shortestPath.add(src);
// Collections.reverse(shortestPath);

// System.out.print("\nShortest Path from " + src + " to " + dst + ": ");
// for (String s : shortestPath) {
// System.out.print(s + " --> ");
// Thread.sleep(1000);
// }
// System.out.println(distance.get(dst) + " km");
// }
// }

// public static void main(String[] args) throws InterruptedException {
// ArrayList<Edge>[] graph = new ArrayList[numStops];
// initialiseRoutes(graph);

// while (true) {
// System.out.println("\n0 -> Exit.");
// System.out.println("1 -> Display all busStops.");
// System.out.println("2 -> Display all neighbouring busStops with distance.");
// System.out.println("3 -> Display shortest distance from source to all
// busStops");
// System.out.println("4 -> Display shortest path from source to destination");
// int choice;
// try {
// choice = sc.nextInt();
// } catch (Exception e) {
// System.out.println("Enter your choice in numbers only.");
// choice = -1;
// sc.nextLine();
// }
// switch (choice) {
// case 0:
// System.exit(0);
// break;
// case 1:
// displayStops(100);
// break;
// case 2:
// displayStops(50);
// int c;
// try {
// System.out.print("\nEnter stop number whose neighbours you want to display :
// ");
// c = sc.nextInt();
// if (c > 0 && c < 34) {
// graph[c - 1].sort(Comparator.comparing(Edge::getDistance));
// displaySorted(graph[c - 1]);
// break;
// } else {
// System.out.println("\nEnter valid stop number.\n");
// }
// } catch (Exception e) {
// System.out.println("\n" + e + "\n");
// System.out.println("Enter your choice in numbers only.");
// sc.nextLine();
// }
// break;
// case 3:
// displayStops(50);
// try {
// System.out.print("\nEnter the starting point : ");
// int st = sc.nextInt();
// if (st > 0 && st < 34) {
// findShortestPath(graph, busStops[st - 1],null);
// break;
// } else {
// System.out.println("\nEnter valid stop number.\n");
// }
// } catch (Exception e) {
// System.out.println("\n" + e + "\n");
// System.out.println("Enter your choice in numbers only.");
// sc.nextLine();
// }
// break;

// case 4:
// displayStops(50);
// try {
// System.out.print("Enter starting point : ");
// int start = sc.nextInt();
// sc.nextLine();
// System.out.print("Enter destination point : ");
// int target = sc.nextInt();

// if (start > 0 && start < 34 && target > 0 && target < 34) {
// findShortestPath(graph, busStops[start - 1], busStops[target - 1]);
// }
// } catch (Exception e) {
// System.out.println("\n" + e + "\n");
// System.out.println("Enter your choice in numbers only.");
// sc.nextLine();
// }
// break;
// default:
// System.out.println("Enter valid choice");
// break;
// }
// }
// }