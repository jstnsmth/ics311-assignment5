package src;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    private HashMap<Island, LinkedList<Route>>adjacentList;

    public Graph() {
        adjacentList = new HashMap<Island, LinkedList<Route>>();
    }

    public void addIsland(Island island) {
        adjacentList.putIfAbsent(island, new LinkedList<Route>());
    }

    public void addRoute(Island start, Island end, int travelTime) {
        if (start != null && end != null) {
            adjacentList.get(start).add(new Route(end, travelTime));
        }
        else {
            System.out.println("Error: Cannot add route, one of the islands can not be found.");
        }
    }

    public LinkedList<Route> getIslandRoutes(Island island) {
        return adjacentList.getOrDefault(island, new LinkedList<Route>());
    }

    public Island[] getAllIslands() {
        Island[] allIslands = adjacentList.keySet().toArray(new Island[0]);
        return allIslands;
    }

    public Island getIslandByName(String islandName) {
        Island[] islands = adjacentList.keySet().toArray(new Island[0]);

        for (int i = 0; i < islands.length; i++) {
            if (islands[i].getName().equals(islandName)) {
                return islands[i];
            }
        }
        return null;
    }

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
}
