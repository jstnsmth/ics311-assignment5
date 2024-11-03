package src;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        // Just for testing purposes
        Graph graph = new Graph();
        graph.loadGraphData("data/island-data.txt");
        graph.displayGraph();

        Island startIsland = graph.getIslandByName("Hawaii");
        HashMap<Island, Integer> shortestPaths = Main.prompt3(startIsland);
        System.out.println("Shortest paths from Hawai'i: ");
        for (Island island: shortestPaths.keySet()) {
            System.out.println(island.getName() + " -> " + shortestPaths.get(island));
        }
    }

    // Dijkstra's algorithm for question 3
    public static HashMap<Island, Integer> prompt3(Island startIsland) {
        Graph graph = new Graph();
        graph.loadGraphData("data/island-data.txt");

        HashMap<Island, Integer> shortestPaths = new HashMap<>();
        PriorityQueue<Island> minPQ = new PriorityQueue<>(Comparator.comparingInt(island -> island.getEstimator()));

        // Initialization function
        // sets estimator for each island in graph to infinity
        for (Island island: graph.getAllIslands()) {
            // sets estimator of source island to 0 so that it is first in minPQ
            if(island.equals(startIsland)) {
                island.setEstimator(0);
                shortestPaths.put(island,0);
            } else {
                island.setEstimator(Integer.MAX_VALUE);
                shortestPaths.put(island, Integer.MAX_VALUE);
            }
            minPQ.add(island);
        }

        //Island firstIsland = minPQ.poll();
        //System.out.println(firstIsland.getName());

        while(!minPQ.isEmpty()) {
            Island currentIsland = minPQ.poll();
            //System.out.println(currentIsland);
            int currentDistance = currentIsland.getEstimator();

            for (Route route: graph.getIslandRoutes(currentIsland)) {
                Island neighbor = route.getDestination();
                int newDist = currentDistance + route.getTravelTime();

                if (newDist < shortestPaths.get(neighbor)) {
                    shortestPaths.put(neighbor,newDist);
                    neighbor.setEstimator(newDist);
                }
            }
        }
        return shortestPaths;
    }
}