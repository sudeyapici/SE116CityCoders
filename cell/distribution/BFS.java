package distribution;

import cell.Cell;
import cell.Connectable;
import cell.provider.utility.UtilityProvider;
import cell.zone.IndustrialZone;
import cell.zone.Zone;
import map.CityMap;
import simulation.OutputPrinter;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    private static final int[] ROW_DIRECTIONS = {-1, 1, 0, 0};
    private static final int[] COL_DIRECTIONS = {0, 0, -1, 1};

    public void distributeUtility(CityMap cityMap, UtilityProvider provider,
                                  OutputPrinter outputPrinter) {
        boolean[][] visited = new boolean[cityMap.getRows()][cityMap.getCols()];
        Queue<Cell> queue = new LinkedList<>();

        int providerRow = provider.getRow();
        int providerCol = provider.getCol();

        visited[providerRow][providerCol] = true;

        for (int i = 0; i < ROW_DIRECTIONS.length; i++) {
            int newRow = providerRow + ROW_DIRECTIONS[i];
            int newCol = providerCol + COL_DIRECTIONS[i];

            if (cityMap.isInside(newRow, newCol)) {
                Cell neighbor = cityMap.getCell(newRow, newCol);

                if (neighbor instanceof Connectable) {
                    visited[newRow][newCol] = true;
                    queue.add(neighbor);
                }
            }
        }

        while (!queue.isEmpty() && provider.getCapacity() > 0) {
            Cell currentCell = queue.poll();

            if (currentCell instanceof Zone) {
                Zone zone = (Zone) currentCell;

                if (!shouldSkipUtility(zone, provider.getUtilityType())) {
                    int requestedAmount = zone.getUtilityDemand()
                            - alreadyReceived(zone, provider.getUtilityType());

                    if (requestedAmount > 0) {
                        int givenAmount = provider.provideUtility(requestedAmount);

                        giveUtilityToZone(zone, provider.getUtilityType(), givenAmount);
                        outputPrinter.printUtilityReceived(zone, provider.getUtilityType(), givenAmount);
                    }
                }
            }

            for (int i = 0; i < ROW_DIRECTIONS.length; i++) {
                int newRow = currentCell.getRow() + ROW_DIRECTIONS[i];
                int newCol = currentCell.getCol() + COL_DIRECTIONS[i];

                if (cityMap.isInside(newRow, newCol) && !visited[newRow][newCol]) {
                    Cell neighbor = cityMap.getCell(newRow, newCol);

                    if (neighbor instanceof Connectable) {
                        visited[newRow][newCol] = true;
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    private boolean shouldSkipUtility(Zone zone, String utilityType) {
        return utilityType.equals("internet") && zone instanceof IndustrialZone;
    }

    private int alreadyReceived(Zone zone, String utilityType) {
        if (utilityType.equals("electricity")) {
            return zone.getReceivedElectricity();
        } else if (utilityType.equals("water")) {
            return zone.getReceivedWater();
        } else if (utilityType.equals("internet")) {
            return zone.getReceivedInternet();
        }

        return 0;
    }

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
