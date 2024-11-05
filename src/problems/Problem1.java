package src.problems;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import src.core.Island;
import src.core.Graph;
import src.core.Route;

public class Problem1 {
    private Graph graph;
    private Island baseIsland;
    private HashMap<Island, Integer> lastVisitTimes;
    private Set<Island> visitedIslands;

    public Problem1(Graph graph, Island start) {
        this.graph = graph;
        this.baseIsland = start;
        this.lastVisitTimes = new HashMap<>();
        this.visitedIslands = new HashSet<>();
        initLastVisitTimes();
    }

    // Initialize the last visit times for each island
    private void initLastVisitTimes() {
        for (Island island : graph.getAllIslands()) {
            lastVisitTimes.put(island, 0);
        }
    }

    // Sort islands by population and recency
    private List<Island> getSortedIslands() {
        List<Island> sortedIslands = new ArrayList<>(graph.getAllIslands());
        sortedIslands.remove(baseIsland);  // Remove the base island from the list

        // Sort by population descending, then by recency ascending
        sortedIslands.sort((a, b) -> {
            int populationComparison = Integer.compare(b.getPopulation(), a.getPopulation());
            return (populationComparison != 0) ? populationComparison : Integer.compare(lastVisitTimes.get(a), lastVisitTimes.get(b));
        });

        return sortedIslands;
    }

    // Find the shortest path to the selected island using Dijkstra's algorithm
    private List<Island> findShortestPath(Island destination) {
        HashMap<Island, Integer> travelTimes = new HashMap<>();
        HashMap<Island, Island> previousIslands = new HashMap<>();
        PriorityQueue<Route> queue = new PriorityQueue<>(Comparator.comparingInt(Route::getTravelTime));
        Set<Island> visited = new HashSet<>();
        List<Island> path = new ArrayList<>();

        for (Island island : graph.getAllIslands()) {
            travelTimes.put(island, Integer.MAX_VALUE);
        }
        travelTimes.put(baseIsland, 0);
        queue.add(new Route(baseIsland, 0));

        while (!queue.isEmpty()) {
            Route currentRoute = queue.poll();
            Island currentIsland = currentRoute.getDestination();

            if (!visited.add(currentIsland)) continue;  // Skip if already been visited

            if (currentIsland.equals(destination)) {
                // Build the path from start to destination
                for (Island at = destination; at != null; at = previousIslands.get(at)) {
                    path.add(0, at);
                }
                break;  // Exit loop after finding the destination
            }

            for (Route neighborRoute : graph.getIslandRoutes(currentIsland)) {
                Island neighborIsland = neighborRoute.getDestination();
                int newTravelTime = travelTimes.get(currentIsland) + neighborRoute.getTravelTime();

                if (newTravelTime < travelTimes.get(neighborIsland)) {
                    travelTimes.put(neighborIsland, newTravelTime);
                    previousIslands.put(neighborIsland, currentIsland);
                    queue.add(new Route(neighborIsland, newTravelTime));
                }
            }
        }

        System.out.print("Path to " + destination.getName() + ": ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getName());
            if (i < path.size() - 1) {
                System.out.print(" -> "); 
            }
        }
        System.out.println();

        return path;
    }



    // Start the knowledge-sharing journey for a specified number of rounds
    public void startKnowledgeSharing(int rounds) {
        for (int i = 0; i < rounds; i++) {
            System.out.println("Round " + (i + 1) + ":");
            List<Island> visitedThisRound = new ArrayList<>();

            // Get the highest-priority islands to visit
            List<Island> sortedIslands = getSortedIslands();
            while (!sortedIslands.isEmpty()) {
                Island targetIsland = sortedIslands.remove(0);

                // Check if the target island has been visited
                if (visitedIslands.contains(targetIsland)) {
                    continue;  // Skip if already visited
                }

                // Find the shortest path to this target island
                List<Island> pathToTarget = findShortestPath(targetIsland);
                if (pathToTarget.isEmpty()) continue; // Skip if there's no valid path

                // Visit each island on the path and update last visit times
                for (Island island : pathToTarget) {
                    System.out.println("Visiting island: " + island.getName() + " with population: " + island.getPopulation());
                    lastVisitTimes.put(island, 0);
                    visitedThisRound.add(island);
                    visitedIslands.add(island);
                }

                // Increment last visit times for all other islands
                for (Island island : lastVisitTimes.keySet()) {
                    if (!visitedThisRound.contains(island)) {
                        lastVisitTimes.put(island, lastVisitTimes.get(island) + 1);
                    }
                }

                baseIsland = targetIsland;
                break;  
            }

            // If all islands have been visited, continue to visit based on last visit time and population
            if (visitedIslands.size() == graph.getAllIslands().size()) {
                System.out.println("All islands have been visited! Continuing to visit based on recency...");
                
                List<Island> candidates = new ArrayList<>();
                for (Island island : graph.getAllIslands()) {
                    if (!visitedThisRound.contains(island) && visitedIslands.contains(island)) {
                        candidates.add(island);
                    }
                }

                // Sort potential islands by last visit time and population
                candidates.sort((a, b) -> {
                    int timeComparison = Integer.compare(lastVisitTimes.get(a), lastVisitTimes.get(b));
                    return (timeComparison != 0) ? timeComparison : Integer.compare(b.getPopulation(), a.getPopulation());
                });

                // Visit the highest-priority island
                if (!candidates.isEmpty()) {
                    Island targetIsland = candidates.get(0); // Highest priority
                    List<Island> pathToTarget = findShortestPath(targetIsland);
                    if (!pathToTarget.isEmpty()) {
                        for (Island island : pathToTarget) {
                            System.out.println("Visiting island: " + island.getName() + " with population: " + island.getPopulation());
                            lastVisitTimes.put(island, 0);  // Reset last visit time for the visited island
                        }
                    }
                }
            }

            System.out.println(); 
        }
    }
}
