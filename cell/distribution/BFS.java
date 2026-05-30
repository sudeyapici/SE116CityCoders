package cell.distribution;

import cell.Cell;
import cell.Connectable;
import cell.provider.utility.UtilityProvider;
import cell.zone.Zone;
import map.CityMap;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    // 4 Directions right left up down
    private static final int[] ROW_DIRECTIONS = {-1, 1, 0, 0};
    private static final int[] COL_DIRECTIONS = {0, 0, -1, 1};

    // Utility distribution is done with BFS.Only Connectable cells are passed.
    // Since Road and Zone are connectible, utility can proceed from them.
    // Since EmptyCell is not connectible, utility cannot pass through there.

    public void distributeUtility(CityMap cityMap, UtilityProvider provider) {
        boolean[][] visited = new boolean[cityMap.getRows()][cityMap.getCols()];
        Queue<Cell> queue = new LinkedList<>();

        int providerRow = provider.getRow();
        int providerCol = provider.getCol();

        visited[providerRow][providerCol] = true;

        // We add Provider's 4 neighbors to the queue as the starting point.

        for (int i = 0; i < ROW_DIRECTIONS.length; i++) {
            int newRow = providerRow + ROW_DIRECTIONS[i];
            int newCol = providerCol + COL_DIRECTIONS[i];

            if (cityMap.isInside(newRow, newCol)) {
                Cell neighbor = cityMap.getCell(newRow, newCol);

                if (neighbor instanceof Connectable) {
                    visited[newRow][newCol] = true;
                    queue.add(neighbor); // // The distribution continues until the queue is empty or the capacity of the provider is finished.

                }
            }
        }

        // The distribution continues until the queue is empty or the capacity of the provider is finished.
        while (!queue.isEmpty() && provider.getCapacity() > 0) {
            Cell currentCell = queue.poll();


            if (currentCell instanceof Zone) {
                Zone zone = (Zone) currentCell;

                int requestedAmount = zone.getUtilityDemand();
                int givenAmount = provider.provideUtility(requestedAmount);

                giveUtilityToZone(zone, provider.getUtilityType(), givenAmount);
            }


            for (int i = 0; i < ROW_DIRECTIONS.length; i++) {
                int newRow = currentCell.getRow() + ROW_DIRECTIONS[i];
                int newCol = currentCell.getCol() + COL_DIRECTIONS[i];

                if (cityMap.isInside(newRow, newCol) && !visited[newRow][newCol]) {
                    Cell neighbor = cityMap.getCell(newRow, newCol);

                    // Only cells that are Connectable are included in BFS.
                    if (neighbor instanceof Connectable) {
                        visited[newRow][newCol] = true;
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    // electricity -> receiveElectricity
    // water -> receiveWater
    // internet -> receiveInternet

    private void giveUtilityToZone(Zone zone, String utilityType, int amount) {
        if (utilityType.equals("electricity")) {
            zone.receiveElectricity(amount);
        } else if (utilityType.equals("water")) {
            zone.receiveWater(amount);
        } else if (utilityType.equals("internet")) {
            zone.receiveInternet(amount);
        }
    }
}

