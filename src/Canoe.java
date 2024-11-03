package src;

public class Canoe {
    private int amount;
    private int capacity;

    public Canoe(){
        this.amount = 1;
        this.capacity = 500;
    }

    public int setAmount(int newAmount){
        this.amount = newAmount;
        return amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getCapacity() {
        return capacity;
    }

    public int setCapacity(int newCapacity) {
        this.capacity = newCapacity;
        return capacity;
    }
}
