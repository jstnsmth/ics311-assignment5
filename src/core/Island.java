package src.core;
import java.util.HashMap;

public class Island {
    private String name;
    private int population;
    private HashMap<String, Integer>resources;
    private int estimator;

    private Island predecessor;
    private HashMap<String, Integer>resourceTime;
    private int receivedResources;

    public Island(String name, int population) {
        this.name = name;
        this.population = population;
        this.resources = new HashMap<String, Integer>();
        this.estimator = 0;
        this.predecessor = null;
        this.resourceTime = new HashMap<String, Integer>();
    }

    public void addResources(String resourceName, int amount) {
        resources.put(resourceName, amount);
    }

    public void addResourceTime(String resourceName, int time) {resourceTime.put(resourceName, time);}

    public int getResourceTime(String resourceName){
        return resourceTime.get(resourceName);
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public int getEstimator() {return estimator; }

    public int setEstimator(int newEstimate) {
        this.estimator = newEstimate;
        return estimator;
    }

    public Island getPredecessor() {return predecessor;}

    public Island setPredecessor(Island island) {
        this.predecessor = island;
        return predecessor;
    }
    public HashMap<String, Integer> getResources() {
        return resources;
    }
    public void addReceivedResources(double amount) {
        receivedResources += amount;
    }

    public double getReceivedResources() {
        return receivedResources;
    }
}
