package cell.distribution;

import cell.Cell;
import cell.provider.utility.UtilityProvider;
import map.CityMap;

public class UtilityDistributor {

    private BFS bfs;

    public UtilityDistributor() {
        this.bfs = new BFS();

        // This method finds all UtilityProvider cells on the map.
        // For each provider, the capacity is first reset from zero to 100.Then utility distribution is made with BFS.
    }


    public void distributeUtilities(CityMap cityMap) {
        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof UtilityProvider) {
                    UtilityProvider provider = (UtilityProvider) cell;

                    // At the beginning of each tick, the provider starts again with 100 capacity.
                    provider.resetCapacity();

                    // The utility distribution is dropped to the BFS class.
                    bfs.distributeUtility(cityMap, provider);
                }
            }
        }
    }
}
