package src;
import src.core.Island;
import src.core.Graph;
import src.problems.Problem2;
import src.problems.Problem1;

public class Main {
    public static void main(String[] args) {
        // Problem 1
        System.out.println("-------- Problem 1 --------");
        Graph graph = new Graph();
        graph.loadGraphData("data/island-data.txt");
        Problem1 problem1 = new Problem1(graph, graph.getIslandByName("Hawaii"));
        problem1.startKnowledgeSharing(6);

        // Problem 2


        // Problem 4
        System.out.println("-------- Problem 4 --------");

        

    }
}
