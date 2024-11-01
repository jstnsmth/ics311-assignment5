package src;
import java.util.HashMap;
import java.util.ArrayList;

public class Graph {
    private HashMap<Island, ArrayList<Route>>adjacentList;

    public Graph() {
        adjacentList = new HashMap<Island, ArrayList<Route>>();
    }

    public void addIsland(Island island) {
        adjacentList.putIfAbsent(island, new ArrayList<Route>());
    }

    public void addRoute(Island start, Island end, int travelTime) {
        adjacentList.get(start).add(new Route(end, travelTime));
    }

    public ArrayList<Route> getIslandRoutes(Island island) {
        return adjacentList.getOrDefault(island, new ArrayList<Route>());
    }
}
