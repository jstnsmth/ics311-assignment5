package src;
import src.core.Island;
import src.core.Graph;
import src.problems.Problem1;
import src.problems.Problem2;

import static src.problems.Problem3.prompt3;

public class Main {
    public static void main(String[] args) {

        // Problem 1
        System.out.println("-------- Problem 1 --------");
        Graph graph = new Graph();
        graph.loadGraphData("data/island-data.txt");
        Problem1 problem1 = new Problem1(graph, graph.getIslandByName("Hawaii"));
        problem1.startKnowledgeSharing(6);

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

        // Problem 3
        System.out.println("-------- Problem 3 --------");
        Graph graph3 = new Graph();
        graph3.loadGraphData("data/island-data.txt");

        System.out.println("Shortest paths from Hawai'i: ");
        Island startIsland = graph.getIslandByName("Hawaii");
        prompt3(graph, startIsland,100,2, 50);

        //printPath(graph, startIsland);
    }
}

