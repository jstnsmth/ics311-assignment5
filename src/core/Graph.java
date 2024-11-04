package src.core;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    public double canoeCapacity;
    private HashMap<Island, LinkedList<Route>>adjacentList;
    private HashMap<String, Island> islandMap; // Map to retrive islands by name

    // Constructors
    public Graph() {
        this.adjacentList = new HashMap<Island, LinkedList<Route>>();
        this.islandMap = new HashMap<String, Island>();
    }

    public Graph(double canoeCapacity) {
        this.canoeCapacity = canoeCapacity;
        this.adjacentList = new HashMap<Island, LinkedList<Route>>();
        this.islandMap = new HashMap<String, Island>();
    }

    // Add an island to the graph
    public void addIsland(Island island) {
        adjacentList.putIfAbsent(island, new LinkedList<Route>());
        islandMap.put(island.getName(), island); // Store island by name for retrieval
    }

    // Add a route between two islands
    public void addRoute(Island start, Island end, int travelTime) {
        if (start != null && end != null) {
            adjacentList.get(start).add(new Route(end, travelTime));
        }
        else {
            System.out.println("Error: Cannot add route, one of the islands can not be found.");
        }
    }

    // Get routes for a specific island
    public LinkedList<Route> getIslandRoutes(Island island) {
        return adjacentList.getOrDefault(island, new LinkedList<Route>());
    }

    // Retrive an island by its name
    public Island getIslandByName(String name) {
        return islandMap.get(name);
    }

    // Get all islands in the graph
    public Collection<Island> getAllIslands() {
        return islandMap.values();
    }

    // Load example into graph from text file
    public void loadGraphData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String[] parts = line.split(" ");
                    if (parts[0].equals("Island")) {
                        String islandName = parts[1];
                        int population = Integer.parseInt(parts[2]);
                        Island island = new Island(islandName, population);
                        addIsland(island);
                    }

                    if (parts[0].equals("Route")) {
                        Island islandStart = getIslandByName(parts[1].trim());
                        Island islandEnd = getIslandByName(parts[2].trim());
                        int travelTime = Integer.parseInt(parts[3]);
                        addRoute(islandStart, islandEnd, travelTime);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the data from file: " + e.getMessage());
        }
    }

    public void displayGraph() {
        boolean spacing = false;
        for (Island island : adjacentList.keySet()) {
            System.out.print("Island: " + island.getName() + "\nRoutes: ");
            for (Route route: adjacentList.get(island)) {
                if (!spacing) {
                    System.out.println("-> " + route.getDestination().getName() + " (time: " + route.getTravelTime() + ") ");
                    spacing = true;
                }
                else {
                    System.out.println("        -> " + route.getDestination().getName() + " (time: " + route.getTravelTime() + ") ");
                }
            }
            spacing = false;
            System.out.println('\n');
        }
    }

    // Distribute resources using a modified Dijkstra's algorithm
    public HashMap<Island, Island> distributeResources(Island source, double totalResource) {
        PriorityQueue<Route> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.getTravelTime(), b.getTravelTime()));
        HashMap<Island, Double> shortestTime = new HashMap<>();
        HashMap<Island, Island> previousNode = new HashMap<>(); // To reconstruct paths
        List<Island> reachableIslands = new ArrayList<>();

        queue.add(new Route(source, 0));
        shortestTime.put(source, 0.0);

        // First pass: Find all reachable islands to determine even split
        while (!queue.isEmpty()) {
            Route currentRoute = queue.poll();
            Island currentIsland = currentRoute.getDestination();

            // Add to reachable islands if it's not the source
            if (currentIsland != source) {
                reachableIslands.add(currentIsland);
            }

            // Process neighbors
            for (Route neighborRoute : getIslandRoutes(currentIsland)) {
                Island neighbor = neighborRoute.getDestination();
                double newTime = shortestTime.get(currentIsland) + neighborRoute.getTravelTime();

                if (!shortestTime.containsKey(neighbor) || newTime < shortestTime.get(neighbor)) {
                    shortestTime.put(neighbor, newTime);
                    previousNode.put(neighbor, currentIsland); // Track the previous island
                    queue.add(new Route(neighbor, (int) newTime));
                }
            }
        }

        // Calculate even distribution amount
        double evenShare = totalResource / reachableIslands.size();

        // Second pass: Allocate resources evenly to each reachable island
        for (Island island : reachableIslands) {
            double resourceForThisIsland = Math.min(evenShare, canoeCapacity);
            island.addReceivedResources(resourceForThisIsland);
            System.out.println("Allocating " + resourceForThisIsland + " resources to " + island.getName());
        }

        return previousNode; // Return the map of previous nodes for path reconstruction
    }
}
