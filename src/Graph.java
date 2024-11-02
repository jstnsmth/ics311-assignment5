package src;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collection;
import java.util.List;

public class Graph {
    public double canoeCapacity;
    private HashMap<Island, ArrayList<Route>> adjacentList;
    private HashMap<String, Island> islandMap;  // Map to retrieve islands by name

    public Graph() {
        this.adjacentList = new HashMap<>();
        this.islandMap = new HashMap<>();
    }

    public Graph(double canoeCapacity) {
        this.canoeCapacity = canoeCapacity;
        this.adjacentList = new HashMap<>();
        this.islandMap = new HashMap<>();
    }

    public void addIsland(Island island) {
        adjacentList.putIfAbsent(island, new ArrayList<Route>());
        islandMap.put(island.getName(), island);  // Store island by name for retrieval
    }

    public void addRoute(Island start, Island end, int travelTime) {
        adjacentList.get(start).add(new Route(end, travelTime));
    }

    public ArrayList<Route> getIslandRoutes(Island island) {
        return adjacentList.getOrDefault(island, new ArrayList<Route>());
    }

    public Island getIsland(String name) {
        return islandMap.get(name);  // Retrieve island by name
    }

    public Collection<Island> getAllIslands() {
        return islandMap.values();  // Get all islands in the graph
    }

    // Dijkstra's to distribute resources
    public HashMap<Island, Island> distributeResources(Island source, double totalResource) {
        PriorityQueue<Route> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.getTravelTime(), b.getTravelTime()));
        HashMap<Island, Double> shortestTime = new HashMap<>();
        HashMap<Island, Island> previousNode = new HashMap<>();  // To reconstruct paths
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
                    previousNode.put(neighbor, currentIsland);  // Track the previous island
                    queue.add(new Route(neighbor, (int)newTime));
                }
            }
        }
    
        // Calculate even distribution amount
        double evenShare = totalResource / reachableIslands.size();
    
        // Second pass: Allocate resources evenly to each reachable island
        for (Island island : reachableIslands) {
            double resourceForThisIsland = Math.min(evenShare, canoeCapacity);
            island.addReceivedResources(resourceForThisIsland);
        }
    
        return previousNode;  // Return the map of previous nodes for path reconstruction
    }
    
}