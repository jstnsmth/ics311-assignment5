package src;
import src.core.Island;
import src.core.Graph;
import src.problems.Problem2;

public class Main {
    public static void main(String[] args) {
        // Problem 1
        System.out.println("-------- Problem 1 --------");



        // Problem 2
        System.out.println("-------- Problem 2 --------");
        Graph graph2 = new Graph(300);
        int totalResource = 300;
        graph2.loadGraphData("data/island-data.txt");
        Island niihau = graph2.getIslandByName("Niihau");

        if (niihau != null) {
            Problem2.distributeResources(graph2, niihau, totalResource);
        }
        else {
            System.out.println("Error: Source island not found.");
        }



        // Problem 4
        System.out.println("-------- Problem 4 --------");

        

    }
}
