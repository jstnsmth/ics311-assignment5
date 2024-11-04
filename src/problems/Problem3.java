package src.problems;

import src.core.Graph;
import src.core.Island;
import src.core.Route;

import java.util.*;

public class Problem3 {
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

        List<LinkedList<Island>> paths = new ArrayList<>();

        for (Island destination : graph.getAllIslands()) {
            if (destination != startIsland) {
                LinkedList<Island> path = new LinkedList<>();
                Island current = destination;
                while (current != null) {
                    path.addFirst(current);
                    current = current.getPredecessor();
                }
                if (path.getFirst() == startIsland) {
                    paths.add(path);
                }
            }
        }

        for (LinkedList<Island> path : paths) {

            int currentCapacity = canoeCapacity;
            int canoesLeft = numCanoes;

            for (Island island : path) {
                System.out.println("Visiting " + island.getName() + ", Remaining capacity: " + currentCapacity + " Time(distribute and plant): " + island.getEstimator() );
                if (currentCapacity <= 0) {
                    if (canoesLeft > 1) {
                        canoesLeft--;
                        currentCapacity = canoeCapacity;
                        System.out.println("Sending another canoe. Canoes remaining: " + canoesLeft);
                    } else {
                        System.out.println("No more canoes available. Cannot continue path to " + island.getName());
                        System.out.println("You must return to " + startIsland.getName() + " if possible.");
                        break;
                    }
                }
                currentCapacity -= resourceUsePerIsland;
            }
            System.out.println("\n");
        }

    }

    /**public static void printPath(Graph graph, Island startIsland) {

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

     System.out.println("Path from " + startIsland.getName() + " to " + island.getName() + " ( " + island.getEstimator() + " ) ");
     for (Island node : path) {
     System.out.print(node.getName());
     if (node != island) {
     System.out.print(" -> ");
     }
     }
     System.out.println();
     }
     }*/

}
