package cell.provider.utility;

public class InternetHub extends UtilityProvider {

    public InternetHub(int row, int col) {
        super(row, col, 'T');
    }

    @Override
    public String getUtilityType() {
        return "internet";
    }
}
