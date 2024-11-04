package src.problems;
import src.core.Graph;
import src.core.Island;
import src.core.Route;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Problem1 {
    private Graph graph;
    private Island baseIsland;
    private HashMap<Island, Integer> lastVisitTimes;

    public Problem1(Graph graph, Island start) {
        this.graph = graph;
        this.baseIsland = start;
        this.lastVisitTimes = new HashMap<Island, Integer>();
    }

    // Initalize the last visit times for each island
    private void initLastVisitTimes() {
        for (Island island : graph.getAllIslands()) {
            lastVisitTimes.put(island, 0);
        }
    }

    private boolean recentlyVisited(Island island) {
        final int recentThreshold = 2;
        if (lastVisitTimes.get(island) < recentThreshold) {
            return true;
        }
        else {
            lastVisitTimes.put(island, 0);
            return false;
        }
    }

    // Prioritze high-population islands and calculate route
    private void visitHighPriorityIslands() {
        Set<Island> visitedIslands = new HashSet<>();
        PriorityQueue<Island> islandQueue = new PriorityQueue<>((a, b) -> {
            int populationComparison = Integer.compare(b.getPopulation(), a.getPopulation());
            return (populationComparison != 0) ? populationComparison : Integer.compare(lastVisitTimes.get(a), lastVisitTimes.get(b));
        });

        // Add all islands to the priority queue
        islandQueue.addAll(graph.getAllIslands());

        // Process islands in the order of priority
        while (!islandQueue.isEmpty()) {
            Island currentIsland = islandQueue.poll();

            if (!currentIsland.equals(baseIsland) && recentlyVisited(currentIsland)) {
                System.out.println("Visiting island: " + currentIsland.getName() + " with population: " + currentIsland.getPopulation());
                visitedIslands = findShortestPath(baseIsland, currentIsland);
                
                for (Island visited : visitedIslands) {
                    lastVisitTimes.put(visited, lastVisitTimes.get(visited) + 1);
                }
            }
        }
        System.out.println("Last visit values:");
        for (Island island : graph.getAllIslands()) {
            System.out.println("Island: " + island.getName() + " value: " + lastVisitTimes.get(island));
        }
        System.out.println("\n");
    }

    private Set<Island> findShortestPath(Island start, Island destination) {
        HashMap<Island, Integer> travelTimes = new HashMap<>();
        HashMap<Island, Island> previousIslands = new HashMap<>();
        Set<Island> visitedIslands = new HashSet<>();
        PriorityQueue<Route> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.getTravelTime(), b.getTravelTime()));

        // Initalize travel times
        for (Island island : graph.getAllIslands()) {
            travelTimes.put(island, Integer.MAX_VALUE);
        }
        travelTimes.put(start, 0);
        queue.add(new Route(start, 0));

        while (!queue.isEmpty()) {
            Route currentRoute = queue.poll();
            Island currentIsland = currentRoute.getDestination();

            // Go through all neighboring routes
            for (Route neighborRoute : graph.getIslandRoutes(currentIsland)) {
                Island neighborIsland = neighborRoute.getDestination();
                int newTravelTime = travelTimes.get(currentIsland) + neighborRoute.getTravelTime();

                if (newTravelTime < travelTimes.get(neighborIsland)) {
                    travelTimes.put(neighborIsland, newTravelTime);
                    previousIslands.put(neighborIsland, currentIsland);
                    queue.add(new Route(neighborIsland, newTravelTime));
                }
            }
            visitedIslands.add(currentIsland);
        }

        // Print the shortest path details for the current journey
        printPathDetails(start, destination, previousIslands, travelTimes);
        return visitedIslands;
    }

    private void printPathDetails(Island start, Island destination, HashMap<Island, Island> previousIslands, HashMap<Island, Integer> travelTimes) {
        if (travelTimes.get(destination) == Integer.MAX_VALUE) {
            System.out.println("No path was found from " + start.getName() + " to " + destination.getName());
        }
        else {
            System.out.print("Path to " + destination.getName() + ": ");
            Island curr = destination;
            while (curr != null && !curr.equals(start)) {
                System.out.print(curr.getName() + " <- ");
                curr = previousIslands.get(curr);
            }
            System.out.println(start.getName());
            System.out.println("Total travel time: " + travelTimes.get(destination));
        }
    }

    // Method to start knowledge-sharing journey
    public void startKnowledgeSharing(int rounds) {
        initLastVisitTimes();
        for (int i = 0; i < rounds; i++) {
            visitHighPriorityIslands();
        }
    }
}
