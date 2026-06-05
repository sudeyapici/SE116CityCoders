package distribution;

import cell.Cell;
import cell.provider.utility.UtilityProvider;
import map.CityMap;
import simulation.OutputPrinter;

public class UtilityDistributor {

    private BFS bfs;

    public UtilityDistributor() {
        this.bfs = new BFS();
    }

    public void distributeUtilities(CityMap cityMap, OutputPrinter outputPrinter) {
        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof UtilityProvider) {
                    UtilityProvider provider = (UtilityProvider) cell;

                    provider.resetCapacity();

                    bfs.distributeUtility(cityMap, provider, outputPrinter);
                }
            }
        }
    }
}
