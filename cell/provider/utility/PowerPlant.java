package cell.provider.utility;

public class PowerPlant extends UtilityProvider {

    public PowerPlant(int row, int col) {
        super(row, col, 'P');
    }

    @Override
    public String getUtilityType() {
        return "electricity";
    }
}
