package cell.provider.service;

import cell.Cell;

public abstract class ServiceProvider extends Cell {

    private final int radius;

    public ServiceProvider(int row, int col, char symbol, int radius) {
        super(row, col, symbol);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public abstract String getServiceType();
}

