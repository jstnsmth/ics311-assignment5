package src;
import src.core.Island;
import src.core.Graph;
import src.problems.Problem2;

import java.sql.Array;
import java.util.*;

import static src.problems.Problem3.prompt3;

public class Main {
    public static void main(String[] args) {
        // Just for testing purposes
        Graph graph = new Graph();
        graph.loadGraphData("data/island-data.txt");
        graph.displayGraph();

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



        // Problem 3
        System.out.println("-------- Problem 3 --------");
        Graph graph3 = new Graph();
        graph3.loadGraphData("data/island-data.txt");


        Island startIsland = graph.getIslandByName("Hawaii");
        prompt3(graph, startIsland,100,2, 50);
        System.out.println("Shortest paths from Hawai'i: ");

        //printPath(graph, startIsland);
    }
}

