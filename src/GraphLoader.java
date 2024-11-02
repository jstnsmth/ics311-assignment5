package src;

public class GraphLoader {
    public static Graph loadGraph(double canoeCapacity) {
        Graph graph = new Graph(canoeCapacity);

        // Add islands (sample data)
        Island niihau = new Island("Niʻihau", 1000);
        Island oahu = new Island("Oʻahu", 1500);
        Island maui = new Island("Maui", 1200);

        graph.addIsland(niihau);
        graph.addIsland(oahu);
        graph.addIsland(maui);

        // Add routes (sample data)
        graph.addRoute(niihau, oahu, 30);
        graph.addRoute(niihau, maui, 45);
        graph.addRoute(oahu, maui, 15);

        return graph;
    }
}
