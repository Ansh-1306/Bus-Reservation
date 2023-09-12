package ds;
import java.util.*;

class DijkstraShortestPath {

    static class Edge {
        String node;
        int weight;

        Edge(String node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public static void findShortestPath(ArrayList<Edge>[] graph, String[] stops, String startNode, String endNode) {
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Initialize the distance map with infinity for all nodes except the start node
        for (String node : stops) {
            distance.put(node, Integer.MAX_VALUE);
        }
        distance.put(startNode, 0);

        // Create a priority queue (min heap) to keep track of nodes with minimum distance
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        queue.add(startNode);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();
            visited.add(currentNode);

            int nodeIndex = find_Index(currentNode, stops);
            ArrayList<Edge> neighbors = graph[nodeIndex];

            for (Edge neighborEdge : neighbors) {
                String neighborNode = neighborEdge.node;
                int edgeWeight = neighborEdge.weight;
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
        String currentNode = endNode;
        while (previous.containsKey(currentNode)) {
            shortestPath.add(currentNode);
            currentNode = previous.get(currentNode);
        }
        shortestPath.add(startNode);
        Collections.reverse(shortestPath);

        System.out.println("Shortest Path from " + startNode + " to " + endNode + ": " + shortestPath);
        System.out.println("Shortest Distance: " + distance.get(endNode));
    }

    private static int find_Index(String node, String[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(node)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] stops = {"A", "B", "C", "D", "E"};
        ArrayList<Edge>[] graph = new ArrayList[stops.length];

        // Initialize the graph with empty ArrayLists
        for (int i = 0; i < stops.length; i++) {
            graph[i] = new ArrayList<>();
        }

        // Add edges and weights to the graph
        graph[0].add(new Edge("B", 2));
        graph[0].add(new Edge("C", 4));
        graph[1].add(new Edge("C", 1));
        graph[1].add(new Edge("D", 7));
        graph[2].add(new Edge("D", 3));
        graph[3].add(new Edge("E", 1));

        String startNode = "A";
        String endNode = "D";

        // findShortestPath(graph, stops, startNode, endNode);
    }
}
