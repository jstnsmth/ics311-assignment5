package src;

import java.sql.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Just for testing purposes
        Graph graph = new Graph();
        graph.loadGraphData("data/island-data.txt");
        graph.displayGraph();

        Island startIsland = graph.getIslandByName("Hawaii");
        prompt3(graph, startIsland);
        System.out.println("Shortest paths from Hawai'i: ");
        for (Island island: graph.getAllIslands()) {
            System.out.println(startIsland.getName() + " -> " + island.getName() + " is " + island.getEstimator()) ;
        }

        printPath(graph, startIsland);
    }

    // Dijkstra's algorithm for question 3
    public static void prompt3(Graph graph, Island startIsland) {
        Set<Island> settled = new HashSet<>();
        PriorityQueue<Island> minPQ = new PriorityQueue<>(Comparator.comparingInt(Island::getEstimator));

        for (Island island : graph.getAllIslands()) {
            island.setEstimator(island.equals(startIsland) ? 0 : Integer.MAX_VALUE);
            minPQ.add(island);
            int randInt = 500 + (int) (Math.random() * ((2000 - 500) + 1));
            island.addResourceTime("Kalo", randInt);
        }


        while(!minPQ.isEmpty()) {
            Island currentIsland = minPQ.poll();
            //System.out.println(currentIsland);
            int currentDistance = currentIsland.getEstimator();

            if (currentDistance == Integer.MAX_VALUE) {
                continue;
            }

            for (Route route: graph.getIslandRoutes(currentIsland)) {
                Island neighbor = route.getDestination();
                if (settled.contains(neighbor)) continue;

                int newDist = currentIsland.getEstimator() + route.getTravelTime() + neighbor.getResourceTime("Kalo");
                if (newDist < neighbor.getEstimator()) {
                    minPQ.remove(neighbor);
                    neighbor.setEstimator(newDist);
                    neighbor.setPredecessor(currentIsland);
                    minPQ.add(neighbor);
                }
            }
        }
    }

    public static void printPath(Graph graph, Island startIsland) {

        for (Island island: graph.getAllIslands()) {
            List<Island> path = new ArrayList<>();
            Island current = island;

            while (current != null) {
                path.add(current);
                current = current.getPredecessor();
            }

            if (path.get(path.size() - 1) != startIsland) {
                System.out.println("No path from " + startIsland.getName() + " to " + island.getName());
                continue;
            }

            Collections.reverse(path);

            System.out.println("Path from " + startIsland.getName() + " to " + island.getName());
            for (Island node : path) {
                System.out.print(node.getName());
                if (node != island) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }

}