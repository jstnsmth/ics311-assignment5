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
        prompt3(graph, startIsland,100,2, 20);
        System.out.println("Shortest paths from Hawai'i: ");
        for (Island island: graph.getAllIslands()) {
            System.out.println(startIsland.getName() + " -> " + island.getName() + " is " + island.getEstimator()) ;
        }

        printPath(graph, startIsland);
    }

    // Dijkstra's algorithm for question 3
    public static void prompt3(Graph graph, Island startIsland, int canoeCapacity, int numCanoes, int resourceUsePerIsland) {
        PriorityQueue<Island> minPQ = new PriorityQueue<>(Comparator.comparingInt(Island::getEstimator));

        for (Island island : graph.getAllIslands()) {
            island.setEstimator(island.equals(startIsland) ? 0 : Integer.MAX_VALUE);
            minPQ.add(island);
            // Assigning a unique amount of time it takes to plant/grow plant on an island in graph.
            int randInt = 500 + (int) (Math.random() * 1500);
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

                int newDist = currentIsland.getEstimator() + route.getTravelTime() + neighbor.getResourceTime("Kalo");
                if (newDist < neighbor.getEstimator()) {
                    minPQ.remove(neighbor);
                    neighbor.setEstimator(newDist);
                    neighbor.setPredecessor(currentIsland);
                    minPQ.add(neighbor);
                }
            }
        }

        int availableCanoes = numCanoes;

        for (Island targetIsland : graph.getAllIslands()) {
            if (targetIsland == startIsland) continue;

            int currentCanoeCapacity = canoeCapacity;
            List<Island> path = new ArrayList<>();
            Island current = targetIsland;

            while (current != null) {
                path.add(current);
                current = current.getPredecessor();
            }

            Collections.reverse(path);

            System.out.println("Distributing resource to " + targetIsland.getName() + " via path: ");
            for (Island island : path) {
                System.out.print(island.getName() + " -> ");

                currentCanoeCapacity -= resourceUsePerIsland;

                if (currentCanoeCapacity <= 0 ) {
                    if (island == startIsland){
                        System.out.println("\nCanoe reloading at source: " + startIsland.getName());
                        currentCanoeCapacity = canoeCapacity;
                    } else if ( availableCanoes > 1 ) {
                        availableCanoes--;
                        System.out.println("\nUsing a new canoe to continue to " + targetIsland.getName());
                    } else {
                        System.out.println("\nNo more canoes available to complete the route to " + targetIsland.getName());
                        break;
                    }
                }
            }
            System.out.println("End of path for resource distribution to " + targetIsland.getName());
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