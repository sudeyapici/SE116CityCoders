package cell.zone;

public class CommercialZone extends Zone {
    private int producedLifestyle;
    private int receivedPopulation;
    private int receivedGoods;

    public CommercialZone(int row, int col) {
        super(row, col, 'C');
        this.receivedPopulation = 0;
        this.receivedGoods = 0;
        this.producedLifestyle = 0;
    }

    public int getProducedLifestyle() {
        return producedLifestyle;
    }

    public int getReceivedPopulation() {
        return receivedPopulation;
    }

    public int getReceivedGoods() {
        return receivedGoods;
    }

    public void receivePopulation(int amount) {
        this.receivedPopulation += amount;
    }

    public void receiveGoods(int amount) {
        this.receivedGoods += amount;
    }


    @Override
    public void resetReceivedValues() {
        super.resetReceivedValues();
        this.receivedPopulation = 0;
        this.receivedGoods = 0;
        this.producedLifestyle = 0;
    }


    @Override
    public void updateLevel() {
        if (getReceivedElectricity() == 0 || getReceivedWater() == 0 || getReceivedInternet() == 0 || receivedPopulation == 0 || receivedGoods == 0) {
            setLevel(0);
        } else {
            int targetLevel = 1;

            if (hasSecurity()) {
                targetLevel = 2;
            }

            int excessPopulation = receivedPopulation - getUtilityDemand();
            int excessGoods = receivedGoods - getUtilityDemand();

            if (targetLevel == 2 && excessPopulation > 0 && excessGoods > 0) {
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
            producedLifestyle = 0;
        } else if (getLevel() == 1) {
            producedLifestyle = getMinimumUtility();
        } else if (getLevel() == 2) {
            producedLifestyle = 2 * getMinimumUtility();
        } else if (getLevel() == 3) {
            producedLifestyle = 2 * getMinimumUtility()
                    + Math.min(receivedPopulation, receivedGoods);
        }

        setUtilityDemand(producedLifestyle);
    }
}

