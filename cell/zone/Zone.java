package cell.zone;

import cell.Cell;
import cell.Connectable;

public abstract class Zone extends Cell implements Connectable {
    private int level;
    private int receivedElectricity;
    private int receivedWater;
    private int receivedInternet;
    private int minimumUtility; // corresponds to m in the project document
    private int utilityDemand;
    private boolean hasSecurity;
    private boolean hasHealth;
    private boolean hasEducation;



    public Zone(int row, int col, char symbol) {
        super(row, col, symbol);
        this.level = 0;
        this.receivedElectricity = 0;
        this.receivedWater = 0;
        this.receivedInternet = 0;
        this.minimumUtility = 0;
        this.utilityDemand = 1;
        this.hasSecurity = false;
        this.hasHealth = false;
        this.hasEducation = false;
    }

    public int getLevel() {
        return level;
    }

    public int getReceivedElectricity() {
        return receivedElectricity;
    }

    public int getReceivedWater() {
        return receivedWater;
    }

    public int getReceivedInternet() {
        return receivedInternet;
    }

    public int getMinimumUtility() {
        return minimumUtility;
    }

    public int getUtilityDemand() {
        return utilityDemand;
    }

    public boolean hasSecurity() {
        return hasSecurity;
    }

    public boolean hasHealth() {
        return hasHealth;
    }

    public boolean hasEducation() {
        return hasEducation;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUtilityDemand(int utilityDemand) {
        this.utilityDemand = Math.max(1, utilityDemand);
    }

    public void receiveElectricity(int amount) {
        this.receivedElectricity += amount;
    }

    public void receiveWater(int amount) {
        this.receivedWater += amount;
    }

    public void receiveInternet(int amount) {
        this.receivedInternet += amount;
    }

    // Called by the service distribution algorithm.
    public void receiveSecurity() {
        this.hasSecurity = true;
    }

    public void receiveHealth() {
        this.hasHealth = true;
    }

    public void receiveEducation() {
        this.hasEducation = true;
    }

    public void calculateMinimumUtility() {
        this.minimumUtility = Math.min(receivedElectricity, Math.min(receivedWater, receivedInternet));
    }

    // Clears tick-based received values before/after a simulation tick.
    public void resetReceivedValues() {
        this.receivedElectricity = 0;
        this.receivedWater = 0;
        this.receivedInternet = 0;
        this.minimumUtility = 0;
        this.hasSecurity = false;
        this.hasHealth = false;
        this.hasEducation = false;
    }


    // Each zone type has different level rules.
    public abstract void updateLevel();

    // Each zone type produces a different resource.
    public abstract void produce();
}
