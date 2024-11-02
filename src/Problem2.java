package src;
import java.util.HashMap;
import java.util.Stack;

public class Problem2 {
    public static void distributeResources(Graph graph, Island sourceIsland, int totalResource) {
        HashMap<Island, Island> previousNode = graph.distributeResources(sourceIsland, totalResource);

        System.out.println("-------- Question 2 --------");
        for (Island island : graph.getAllIslands()) {
            if (!island.equals(sourceIsland)) {
                printPath(previousNode, sourceIsland, island);
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
