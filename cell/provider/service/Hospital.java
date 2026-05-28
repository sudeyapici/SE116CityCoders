package cell.provider.service;

public class Hospital extends ServiceProvider {

    public Hospital(int row, int col) {
        super(row, col, 'D', 3);
    }

    @Override
    public String getServiceType() {
        return "health";
    }
}
