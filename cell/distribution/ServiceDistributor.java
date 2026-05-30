package cell.distribution;

import cell.Cell;
import cell.provider.service.ServiceProvider;
import cell.zone.Zone;
import map.CityMap;

public class ServiceDistributor {

    // Finds all ServiceProvider cells on the map.
    // Service distribution is not done with BFS.
    // Because according to the directive, the services do not want a connection, it only works within the radius.


    public void distributeServices(CityMap cityMap) {
        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof ServiceProvider) {
                    ServiceProvider provider = (ServiceProvider) cell;

                    distributeServiceFromProvider(cityMap, provider);
                }
            }
        }
    }

// Finds the zones in the radius for a single service provider.
// I used Manhattan distance.So distance = |row difference| + |col difference|


    private void distributeServiceFromProvider(CityMap cityMap, ServiceProvider provider) {
        int providerRow = provider.getRow();
        int providerCol = provider.getCol();
        int radius = provider.getRadius();

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof Zone) {
                    int distance = calculateManhattanDistance(providerRow, providerCol, row, col);

                    if (distance <= radius) {
                        Zone zone = (Zone) cell;
                        giveServiceToZone(zone, provider.getServiceType());
                    }
                }
            }
        }
    }


    private int calculateManhattanDistance(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
    }


    // security -> receiveSecurity
    // health -> receiveHealth
    // education -> receiveEducation

    private void giveServiceToZone(Zone zone, String serviceType) {
        if (serviceType.equals("security")) {
            zone.receiveSecurity();
        } else if (serviceType.equals("health")) {
            zone.receiveHealth();
        } else if (serviceType.equals("education")) {
            zone.receiveEducation();
        }
    }
}
