package src;
import java.util.HashMap;

public class Island {
    private String name;
    private int population;
    private HashMap<String, Integer>resources;
    private int estimator;

    public Island(String name, int population) {
        this.name = name;
        this.population = population;
        this.resources = new HashMap<String, Integer>();
        this.estimator = 0;
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
}
