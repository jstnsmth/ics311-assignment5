package src.core;

public class Route {
    private Island destination;
    private int travelTime;
    private int allocatedResources = 0;

    public Route(Island destination, int travelTime) {
        this.destination = destination;
        this.travelTime = travelTime;
    }

    public Island getDestination() {
        return destination;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setAllocatedResources(int amount) {
        this.allocatedResources = amount;
    }

    public int getAllocatedResources() {
        return allocatedResources;
    }
}
