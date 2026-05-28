package cell.provider.utility;

import cell.Cell;

public abstract class UtilityProvider extends Cell {

    private int capacity;

    public UtilityProvider(int row, int col, char symbol) {
        super(row, col, symbol);
        this.capacity = 100;
    }

    public int getCapacity() {
        return capacity;
    }

    public int provideUtility(int requestedAmount) {
        if (requestedAmount < 0) {
            requestedAmount = 0;
        }

        int givenAmount;

        if (capacity < requestedAmount) {
            givenAmount = capacity;
            capacity = 0;
        } else {
            givenAmount = requestedAmount;
            capacity -= requestedAmount;
        }

        return givenAmount;
    }

    public void resetCapacity() {
        this.capacity = 100;
    }

    public abstract String getUtilityType();
}
