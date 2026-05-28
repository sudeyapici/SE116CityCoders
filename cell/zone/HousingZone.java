package cell.zone;

public class HousingZone extends Zone {
    private int producedPopulation;
    private int receivedLifestyle;

    public HousingZone(int row, int col) {
        super(row, col, 'H');
        this.producedPopulation = 0;
        this.receivedLifestyle = 0;
    }

    public int getProducedPopulation() {
        return producedPopulation;
    }

    public int getReceivedLifestyle() {
        return receivedLifestyle;
    }

    public void receiveLifestyle(int amount) {
        this.receivedLifestyle += amount;
    }

    @Override
    public void resetReceivedValues() {
        super.resetReceivedValues();
        this.receivedLifestyle = 0;
        this.producedPopulation = 0;
    }

    @Override
    public void updateLevel() {
        if (getReceivedElectricity() == 0 || getReceivedWater() == 0 || getReceivedInternet() == 0) {
            setLevel(0);
        } else {
            int targetLevel = 1;

            if (hasSecurity() && hasHealth() && hasEducation()) {
                targetLevel = 2;
            }

            if (targetLevel == 2 && receivedLifestyle > 0) {
                targetLevel = 3;
            }

            if (targetLevel > getLevel()) {
                setLevel(getLevel() + 1);
            } else if (targetLevel < getLevel()) {
                setLevel(getLevel() - 1);
            }
        }
    }

    @Override
    public void produce() {
        calculateMinimumUtility();

        if (getLevel() == 0) {
            producedPopulation = 0;
        } else if (getLevel() == 1) {
            producedPopulation = getMinimumUtility();
        } else if (getLevel() == 2) {
            producedPopulation = 2 * getMinimumUtility();
        } else if (getLevel() == 3) {
            producedPopulation = 2 * getMinimumUtility() + receivedLifestyle;
        }

        setUtilityDemand(producedPopulation);
    }
}
