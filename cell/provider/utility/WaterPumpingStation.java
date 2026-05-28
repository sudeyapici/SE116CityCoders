package cell.provider.utility;

public class WaterPumpingStation extends UtilityProvider {

    public WaterPumpingStation(int row, int col) {
        super(row, col, 'W');
    }

    @Override
    public String getUtilityType() {
        return "water";
    }
}
