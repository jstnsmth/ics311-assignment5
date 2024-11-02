package src.core;
import java.util.HashMap;

public class Island {
    private String name;
    private int population;
    private int receivedResources;
    private HashMap<String, Integer> resources;

    public Island(String name, int population) {
        this.name = name;
        this.population = population;
        this.resources = new HashMap<String, Integer>();
    }

    public void addResources(String resourceName, int amount) {
        resources.put(resourceName, amount);
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
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