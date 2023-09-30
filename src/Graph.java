import java.util.*;

public class Graph {
    public static final int numStops = 33;
    static Scanner sc = new Scanner(System.in);
    static String[] stops = { "Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha", "Bharuch", "Bhavnagar",
            "Botad", "Chhota Udaipur", "Dahod", "Dang", "Devbhoomi Dwarka", "Gandhinagar", "Gir Somnath", "Jamnagar",
            "Junagadh", "Kheda", "Kutch", "Mahisagar", "Mehsana", "Morbi", "Narmada", "Navsari", "Panchmahal", "Patan",
            "Porbandar", "Rajkot", "Sabarkantha", "Surat", "Surendranagar", "Tapi", "Vadodara", "Valsad" };

    static class Edge {
        String src;
        String dst;
        int distance;

        private Edge(String src, String dst, int distance) {
            this.src = src;
            this.dst = dst;
            this.distance = distance;
        }

        private int getDistance() {
            return distance;
        }

    }

    private static void displayStops(int delay) throws InterruptedException {
        System.out.println("+-------------------------+");
        System.out.println("|        STOP LIST        |");
        System.out.println("+-------------------------+\n");
        ArrayList<String> stopNames = new ArrayList<>(Arrays.asList(stops));
        int i = 1;
        for (String st : stopNames) {
            Thread.sleep(delay);
            if (i < 10) {
                System.out.println("0" + i++ + " -> " + st);
            } else {
                System.out.println(i++ + " -> " + st);
            }
        }

    }

    private static void initialiseRoutes(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(stops[0], stops[2], 65));
        graph[0].add(new Edge(stops[0], stops[6], 170));
        graph[0].add(new Edge(stops[0], stops[7], 120));
        graph[0].add(new Edge(stops[0], stops[12], 30));
        graph[0].add(new Edge(stops[0], stops[16], 60));
        graph[0].add(new Edge(stops[0], stops[19], 75));
        graph[0].add(new Edge(stops[0], stops[29], 125));

        graph[1].add(new Edge(stops[1], stops[6], 70));
        graph[1].add(new Edge(stops[1], stops[7], 90));
        graph[1].add(new Edge(stops[1], stops[13], 100));
        graph[1].add(new Edge(stops[1], stops[15], 45));
        graph[1].add(new Edge(stops[1], stops[26], 75));

        graph[2].add(new Edge(stops[2], stops[0], 65));
        graph[2].add(new Edge(stops[2], stops[5], 50));
        graph[2].add(new Edge(stops[2], stops[16], 20));
        graph[2].add(new Edge(stops[2], stops[31], 45));

        graph[3].add(new Edge(stops[3], stops[12], 50));
        graph[3].add(new Edge(stops[3], stops[16], 120));
        graph[3].add(new Edge(stops[3], stops[18], 80));
        graph[3].add(new Edge(stops[3], stops[27], 50));

        graph[4].add(new Edge(stops[4], stops[17], 350));
        graph[4].add(new Edge(stops[4], stops[19], 100));
        graph[4].add(new Edge(stops[4], stops[24], 80));
        graph[4].add(new Edge(stops[4], stops[27], 150));

        graph[5].add(new Edge(stops[5], stops[2], 50));
        graph[5].add(new Edge(stops[5], stops[21], 50));
        graph[5].add(new Edge(stops[5], stops[28], 70));
        graph[5].add(new Edge(stops[5], stops[31], 70));

        graph[6].add(new Edge(stops[6], stops[0], 170));
        graph[6].add(new Edge(stops[6], stops[1], 70));
        graph[6].add(new Edge(stops[6], stops[7], 80));

        graph[7].add(new Edge(stops[7], stops[0], 120));
        graph[7].add(new Edge(stops[7], stops[1], 90));
        graph[7].add(new Edge(stops[7], stops[6], 80));
        graph[7].add(new Edge(stops[7], stops[26], 80));
        graph[7].add(new Edge(stops[7], stops[29], 70));

        graph[8].add(new Edge(stops[8], stops[9], 70));
        graph[8].add(new Edge(stops[8], stops[21], 90));
        graph[8].add(new Edge(stops[8], stops[23], 80));
        graph[8].add(new Edge(stops[8], stops[31], 100));

        graph[9].add(new Edge(stops[9], stops[8], 70));
        graph[9].add(new Edge(stops[9], stops[18], 90));
        graph[9].add(new Edge(stops[9], stops[23], 50));

        graph[10].add(new Edge(stops[10], stops[22], 80));
        graph[10].add(new Edge(stops[10], stops[30], 100));

        graph[11].add(new Edge(stops[11], stops[14], 135));
        graph[11].add(new Edge(stops[11], stops[25], 100));

        graph[12].add(new Edge(stops[12], stops[0], 30));
        graph[12].add(new Edge(stops[12], stops[3], 50));
        graph[12].add(new Edge(stops[12], stops[16], 30));
        graph[12].add(new Edge(stops[12], stops[19], 30));
        graph[12].add(new Edge(stops[12], stops[27], 30));

        graph[13].add(new Edge(stops[13], stops[1], 100));
        graph[13].add(new Edge(stops[13], stops[15], 60));

        graph[14].add(new Edge(stops[14], stops[11], 135));
        graph[14].add(new Edge(stops[14], stops[20], 100));
        graph[14].add(new Edge(stops[14], stops[25], 150));
        graph[14].add(new Edge(stops[14], stops[26], 90));

        graph[15].add(new Edge(stops[15], stops[1], 45));
        graph[15].add(new Edge(stops[15], stops[13], 60));
        graph[15].add(new Edge(stops[15], stops[25], 100));
        graph[15].add(new Edge(stops[15], stops[26], 100));

        graph[16].add(new Edge(stops[16], stops[0], 60));
        graph[16].add(new Edge(stops[16], stops[2], 20));
        graph[16].add(new Edge(stops[16], stops[3], 120));
        graph[16].add(new Edge(stops[16], stops[12], 30));
        graph[16].add(new Edge(stops[16], stops[18], 50));
        graph[16].add(new Edge(stops[16], stops[23], 70));
        graph[16].add(new Edge(stops[16], stops[31], 45));

        graph[17].add(new Edge(stops[17], stops[4], 350));
        graph[17].add(new Edge(stops[17], stops[20], 250));
        graph[17].add(new Edge(stops[17], stops[24], 235));
        graph[17].add(new Edge(stops[17], stops[29], 350));

        graph[18].add(new Edge(stops[18], stops[3], 80));
        graph[18].add(new Edge(stops[18], stops[9], 90));
        graph[18].add(new Edge(stops[18], stops[16], 50));
        graph[18].add(new Edge(stops[18], stops[23], 100));

        graph[19].add(new Edge(stops[19], stops[0], 75));
        graph[19].add(new Edge(stops[19], stops[4], 100));
        graph[19].add(new Edge(stops[19], stops[12], 30));
        graph[19].add(new Edge(stops[19], stops[24], 40));
        graph[19].add(new Edge(stops[19], stops[27], 50));
        graph[19].add(new Edge(stops[19], stops[29], 140));

        graph[20].add(new Edge(stops[20], stops[14], 100));
        graph[20].add(new Edge(stops[20], stops[17], 250));
        graph[20].add(new Edge(stops[20], stops[26], 70));
        graph[20].add(new Edge(stops[20], stops[29], 100));

        graph[21].add(new Edge(stops[21], stops[5], 50));
        graph[21].add(new Edge(stops[21], stops[8], 90));
        graph[21].add(new Edge(stops[21], stops[28], 90));
        graph[21].add(new Edge(stops[21], stops[30], 75));
        graph[21].add(new Edge(stops[21], stops[31], 90));

        graph[22].add(new Edge(stops[22], stops[10], 80));
        graph[22].add(new Edge(stops[22], stops[28], 30));
        graph[22].add(new Edge(stops[22], stops[30], 50));
        graph[22].add(new Edge(stops[22], stops[32], 35));

        graph[23].add(new Edge(stops[23], stops[8], 80));
        graph[23].add(new Edge(stops[23], stops[9], 50));
        graph[23].add(new Edge(stops[23], stops[16], 70));
        graph[23].add(new Edge(stops[23], stops[18], 100));
        graph[23].add(new Edge(stops[23], stops[31], 55));

        graph[24].add(new Edge(stops[24], stops[4], 80));
        graph[24].add(new Edge(stops[24], stops[17], 235));
        graph[24].add(new Edge(stops[24], stops[19], 40));
        graph[24].add(new Edge(stops[24], stops[29], 120));

        graph[25].add(new Edge(stops[25], stops[11], 100));
        graph[25].add(new Edge(stops[25], stops[14], 150));
        graph[25].add(new Edge(stops[25], stops[15], 100));
        graph[25].add(new Edge(stops[25], stops[26], 175));

        graph[26].add(new Edge(stops[26], stops[1], 75));
        graph[26].add(new Edge(stops[26], stops[7], 80));
        graph[26].add(new Edge(stops[26], stops[14], 90));
        graph[26].add(new Edge(stops[26], stops[15], 100));
        graph[26].add(new Edge(stops[26], stops[20], 70));
        graph[26].add(new Edge(stops[26], stops[25], 175));
        graph[26].add(new Edge(stops[26], stops[29], 110));

        graph[27].add(new Edge(stops[27], stops[3], 50));
        graph[27].add(new Edge(stops[27], stops[4], 150));
        graph[27].add(new Edge(stops[27], stops[12], 30));
        graph[27].add(new Edge(stops[27], stops[19], 50));

        graph[28].add(new Edge(stops[28], stops[5], 70));
        graph[28].add(new Edge(stops[28], stops[21], 90));
        graph[28].add(new Edge(stops[28], stops[22], 30));
        graph[28].add(new Edge(stops[28], stops[30], 75));

        graph[29].add(new Edge(stops[29], stops[0], 125));
        graph[29].add(new Edge(stops[29], stops[7], 70));
        graph[29].add(new Edge(stops[29], stops[17], 350));
        graph[29].add(new Edge(stops[29], stops[19], 140));
        graph[29].add(new Edge(stops[29], stops[20], 100));
        graph[29].add(new Edge(stops[29], stops[24], 120));
        graph[29].add(new Edge(stops[29], stops[26], 110));

        graph[30].add(new Edge(stops[30], stops[10], 100));
        graph[30].add(new Edge(stops[30], stops[21], 75));
        graph[30].add(new Edge(stops[30], stops[22], 50));
        graph[30].add(new Edge(stops[30], stops[28], 75));

        graph[31].add(new Edge(stops[31], stops[2], 45));
        graph[31].add(new Edge(stops[31], stops[5], 70));
        graph[31].add(new Edge(stops[31], stops[8], 100));
        graph[31].add(new Edge(stops[31], stops[16], 45));
        graph[31].add(new Edge(stops[31], stops[21], 90));
        graph[31].add(new Edge(stops[31], stops[23], 55));

        graph[32].add(new Edge(stops[32], stops[22], 35));

    }

    private static void displaySorted(ArrayList<Edge> graph) throws InterruptedException {
        System.out.println();
        for (Edge e : graph) {
            Thread.sleep(100);
            System.out.printf("%-20s == %10s km\n", e.dst, e.distance);
        }
    }

    private static int find_index(String stop) {
        for (int i = 0; i < numStops; i++) {
            if (stops[i].equals(stop)) {
                return i;
            }
        }
        return -1;
    }

    public static void findShortestPath(ArrayList<Edge>[] graph, String src, String dst) throws InterruptedException {
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Initialize the distance map with infinity for all nodes except the start node
        for (String node : stops) {
            distance.put(node, Integer.MAX_VALUE);
        }
        distance.put(src, 0);

        // Create a priority queue to keep track of nodes with minimum distance
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        queue.add(src);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();
            visited.add(currentNode);

            int nodeIndex = find_index(currentNode);
            ArrayList<Edge> neighbours = graph[nodeIndex];

            for (Edge neighbourEdge : neighbours) {
                String neighborNode = neighbourEdge.dst;
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
            for (String stop : stops) {
                System.out.printf(" %-20s == %10s km\n", stop, distance.get(stop));
                Thread.sleep(100);
            }
        } else {
            List<String> shortestPath = new ArrayList<>();
            String currentNode = dst;
            while (previous.containsKey(currentNode)) {
                shortestPath.add(currentNode);
                currentNode = previous.get(currentNode);
            }
            shortestPath.add(src);
            Collections.reverse(shortestPath);

            System.out.print("\nShortest Path from " + src + " to " + dst + ": ");
            for (String s : shortestPath) {
                System.out.print(s + " --> ");
                Thread.sleep(1000);
            }
            System.out.println(distance.get(dst) + " km");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Edge>[] graph = new ArrayList[numStops];
        initialiseRoutes(graph);

        while (true) {
            System.out.println("\n0 -> Exit.");
            System.out.println("1 -> Display all stops.");
            System.out.println("2 -> Display all neighbouring stops with distance.");
            System.out.println("3 -> Display shortest distance from source to all stops");
            System.out.println("4 -> Display shortest path from source to destination");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Enter your choice in numbers only.");
                choice = -1;
                sc.nextLine();
            }
            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    displayStops(100);
                    break;
                case 2:
                    displayStops(50);
                    int c;
                    try {
                        System.out.print("\nEnter stop number whose neighbours you want to display : ");
                        c = sc.nextInt();
                        if (c > 0 && c < 34) {
                            graph[c - 1].sort(Comparator.comparing(Edge::getDistance));
                            displaySorted(graph[c - 1]);
                            break;
                        } else {
                            System.out.println("\nEnter valid stop number.\n");
                        }
                    } catch (Exception e) {
                        System.out.println("\n" + e + "\n");
                        System.out.println("Enter your choice in numbers only.");
                        sc.nextLine();
                    }
                    break;
                case 3:
                    displayStops(50);
                    try {
                        System.out.print("\nEnter the starting point : ");
                        int st = sc.nextInt();
                        if (st > 0 && st < 34) {
                            findShortestPath(graph, stops[st - 1],null);
                            break;
                        } else {
                            System.out.println("\nEnter valid stop number.\n");
                        }
                    } catch (Exception e) {
                        System.out.println("\n" + e + "\n");
                        System.out.println("Enter your choice in numbers only.");
                        sc.nextLine();
                    }
                    break;

                case 4:
                    displayStops(50);
                    try {
                        System.out.print("Enter starting point : ");
                        int start = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter destination point : ");
                        int target = sc.nextInt();

                        if (start > 0 && start < 34 && target > 0 && target < 34) {
                            findShortestPath(graph, stops[start - 1], stops[target - 1]);
                        }
                    } catch (Exception e) {
                        System.out.println("\n" + e + "\n");
                        System.out.println("Enter your choice in numbers only.");
                        sc.nextLine();
                    }
                    break;
                default:
                    System.out.println("Enter valid choice");
                    break;
            }
        }
    }
}