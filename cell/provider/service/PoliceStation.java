package cell.provider.service;

public class PoliceStation extends ServiceProvider {

    public PoliceStation(int row, int col) {
        super(row, col, 'F', 5);
    }

    @Override
    public String getServiceType() {
        return "security";
    }
}
