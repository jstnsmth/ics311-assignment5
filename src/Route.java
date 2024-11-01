package src;

public class Route {
    private Island destination;
    private int travelTime;

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
}
