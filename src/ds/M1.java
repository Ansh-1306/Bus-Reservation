package ds;
import java.util.*;
class DijkstraShortestPath {

    public static void findShortestPath(Map<String, Map<String, Integer>> graph, String startNode, String endNode) {
        // Initialize the distance and visited maps
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Initialize the distance map with infinity for all nodes except the start node
        for (String node : graph.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
        }
        distance.put(startNode, 0);

        // Create a priority queue (min heap) to keep track of nodes with minimum distance
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        queue.add(startNode);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();
            visited.add(currentNode);

            // Update the distance and previous node for neighboring nodes
            for (Map.Entry<String, Integer> neighbor : graph.get(currentNode).entrySet()) {
                String neighborNode = neighbor.getKey();
                int edgeWeight = neighbor.getValue();
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

        // Reconstruct the shortest path from startNode to endNode
        List<String> shortestPath = new ArrayList<>();
        String currentNode = endNode;
        while (previous.containsKey(currentNode)) {
            shortestPath.add(currentNode);
            currentNode = previous.get(currentNode);
        }
        shortestPath.add(startNode);
        Collections.reverse(shortestPath);

        // Print the shortest path and distance
        System.out.println("Shortest Path from " + startNode + " to " + endNode + ": " + shortestPath);
        System.out.println("Shortest Distance: " + distance.get(endNode));
    }

    public static void main(String[] args) {
        // Create a sample graph
        Map<String, Map<String, Integer>> graph = new HashMap<>();
        graph.put("A", Map.of("B", 2, "C", 4));
        graph.put("B", Map.of("C", 1, "D", 7));
        graph.put("C", Map.of("D", 3));
        graph.put("D", Map.of("E", 1));
        graph.put("E", Collections.emptyMap());

        String startNode = "A";
        String endNode = "D";

        findShortestPath(graph, startNode, endNode);
    }
}
