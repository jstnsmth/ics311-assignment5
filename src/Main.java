package src;

import java.util.HashMap;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // Initialize a graph with a given canoe capacity (e.g., 100 units)
        Graph graph = new Graph(300);

        // Create islands
        Island niihau = new Island("Niʻihau", 1000);
        Island oahu = new Island("Oʻahu", 1500);
        Island maui = new Island("Maui", 1200);
        Island kauai = new Island("Kauaʻi", 1300);

        // Add islands to the graph
        graph.addIsland(niihau);
        graph.addIsland(oahu);
        graph.addIsland(maui);
        graph.addIsland(kauai);

        // Create routes between islands with travel times
        graph.addRoute(niihau, oahu, 30);
        graph.addRoute(niihau, maui, 45);
        graph.addRoute(oahu, kauai, 15);

        // Set the total resource to distribute
        int totalResource = 300;

        // Distribute resources from the source island, Niʻihau
        HashMap<Island, Island> previousNode = graph.distributeResources(niihau, totalResource);
        // Print the shortest path from Niʻihau to each island
        System.out.println("-------- Question 2 --------");
        for (Island island : graph.getAllIslands()) {
            if (island != niihau) { 
                printPath(previousNode, niihau, island);
                System.out.println("Resource amount sent to " + island.getName() + ": " + island.getReceivedResources());
                System.out.println();
            }
        }
    }

    // Helper method to print the path from the source to a destination
    private static void printPath(HashMap<Island, Island> previousNode, Island source, Island destination) {
        Stack<Island> path = new Stack<>();
        Island current = destination;
        
        // Reconstruct route by following previous nodes
        while (current != null && !current.equals(source)) {
            path.push(current);
            current = previousNode.get(current);
        }
        if (current == null) {
            System.out.println("No path found from " + source.getName() + " to " + destination.getName());
            return;
        }
        
        path.push(source); 
        
        System.out.print("Shortest path from " + source.getName() + " to " + destination.getName() + ": ");
        while (!path.isEmpty()) {
            System.out.print(path.pop().getName());
            if (!path.isEmpty()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}



public class Main {
    public static void main(String[] args) {
        // Just for testing purposes
        Graph graph = new Graph();
        graph.loadGraphData("data/island-data.txt");
        graph.displayGraph();
    }
}
