import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;


public class Graph {
    private static final int NUM_STOPS = 33;
    private static ArrayList<Edge>[] graph;
    static Scanner sc ;

    // Array to store stop names.
    static String[] stops = { "Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha", "Bharuch", "Bhavnagar",
            "Botad", "Chhota Udaipur", "Dahod", "Dang", "Devbhoomi Dwarka", "Gandhinagar", "Gir Somnath", "Jamnagar",
            "Junagadh", "Kheda", "Kutch", "Mahisagar", "Mehsana", "Morbi", "Narmada", "Navsari", "Panchmahal", "Patan",
            "Porbandar", "Rajkot", "Sabarkantha", "Surat", "Surendranagar", "Tapi", "Vadodara", "Valsad" };

    // static class to define edges in graph.
    static class Edge {
        String src;
        String dst;
        int distance;

        private Edge(String src, String dst, int distance) {
            this.src = src;
            this.dst = dst;
            this.distance = distance;
        }
    }

    // Method to implement and initialise graph.
    private static void initialiseRoutes() {
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

    // Method to display stops.
    public static void displayStops() throws InterruptedException {
        ArrayList<String> stopNames = new ArrayList<>(Arrays.asList(stops));
        System.out
                .println("            +-----------------------------------------------------------------------------+");
        System.out
                .println("            |                                                                             |");
        System.out.println(
                "            |                                 \u001B[1mSTOPS LIST\u001B[0m                                  |");
        System.out
                .println("            |                                                                             |");
        System.out
                .println("            +-----------------------------------------------------------------------------+");
        for (int i = 0; i < stopNames.size(); i += 3) {
            Thread.sleep(100);
            String stop1 = String.format("%-30s", "\u001B[33m[" + (i + 1) + "] " + stopNames.get(i));
            String stop2 = String.format("%-30s", "\u001B[35m[" + (i + 2) + "] " + stopNames.get(i + 1));
            String stop3 = String.format("%-30s", "\u001B[36m[" + (i + 3) + "] " + stopNames.get(i + 2));

            System.out.println(
                    "            |                                                                             |");
            System.out.println("            | " + stop1 + stop2 + stop3 + "\u001B[0m |");
        }
        System.out
                .println("            |                                                                             |");
        System.out
                .println("            +-----------------------------------------------------------------------------+");
        System.out.println();

    }

    // To find index of stop in the array.
    private static int find_index(String stop) {
        for (int i = 0; i < NUM_STOPS; i++) {
            if (stops[i].equalsIgnoreCase(stop)) {
                return i;
            }
        }
        return -1;
    }

    // Modified dijkstra's Algorithm to fin shortest path from source to destination.
    public static int findShortestPath(String src, String dst) {
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        for (String node : stops) {
            distance.put(node, Integer.MAX_VALUE);
        }
        distance.put(src, 0);

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
        List<String> shortestPath = new ArrayList<>();
        String currentNode = dst;
        while (previous.containsKey(currentNode)) {
            shortestPath.add(currentNode);
            currentNode = previous.get(currentNode);
        }
        shortestPath.add(src);
        Collections.reverse(shortestPath);

        System.out.print("\n                  Path from " + src + " to " + dst + ": ");
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            System.out.print(shortestPath.get(i) + " --> ");
        }
        System.out.println(shortestPath.get(shortestPath.size() - 1));
        System.out.println("                  Distance : " + distance.get(dst) + " km");
        return distance.get(dst);
        // }
    }

    public Graph(Scanner sc) {
        graph = new ArrayList[NUM_STOPS];
        initialiseRoutes();
        Graph.sc=sc;
    }
}
