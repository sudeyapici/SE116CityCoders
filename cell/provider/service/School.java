package cell.provider.service;

public class School extends ServiceProvider {

    public School(int row, int col) {
        super(row, col, 'S', 4);
    }

    @Override
    public String getServiceType() {
        return "education";
    }
}
