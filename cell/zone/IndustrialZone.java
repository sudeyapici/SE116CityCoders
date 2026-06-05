package cell.zone;

public class IndustrialZone extends Zone {
    private int producedGoods;
    private int receivedPopulation;


    public IndustrialZone(int row, int col) {
        super(row, col, 'I');
        this.receivedPopulation = 0;
        this.producedGoods = 0;
    }

    public int getReceivedPopulation() {
        return receivedPopulation;
    }

    public int getProducedGoods() {
        return producedGoods;
    }

    public void receivePopulation(int amount) {
        this.receivedPopulation += amount;
    }

    @Override
    public void resetReceivedValues() {
        super.resetReceivedValues();
        this.receivedPopulation = 0;
        this.producedGoods = 0;
    }

    @Override
    public void calculateMinimumUtility() {
        setMinimumUtility(Math.min(getReceivedWater(), getReceivedElectricity()));
    }

    @Override
    protected boolean hasRequiredUtilities() {
        return getReceivedElectricity() >= getUtilityDemand()
                && getReceivedWater() >= getUtilityDemand();
    }

    @Override
    public void updateLevel() {
        if (!hasRequiredUtilities() || receivedPopulation == 0) {
            setLevel(0);
        } else {
            int targetLevel = 1;

            if (hasSecurity()) {
                targetLevel = 2;
            }

            int excessPopulation = receivedPopulation - getUtilityDemand();

            if (targetLevel == 2 && excessPopulation > 0) {
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
            producedGoods = 0;
        } else if (getLevel() == 1) {
            producedGoods = getMinimumUtility();
        } else if (getLevel() == 2) {
            producedGoods = 2 * getMinimumUtility();
        } else if (getLevel() == 3) {
            producedGoods = 2 * getMinimumUtility() + receivedPopulation;
        }

        setUtilityDemand(producedGoods);
    }
}
