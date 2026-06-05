package simulation;

import cell.Cell;
import cell.zone.Zone;
import distribution.ServiceDistributor;
import distribution.UtilityDistributor;
import map.CityMap;

public class Simulation {
    private CityMap cityMap;
    private ServiceDistributor serviceDistributor;
    private UtilityDistributor utilityDistributor;
    private ResourcePool resourcePool;
    private OutputPrinter outputPrinter;

    public Simulation(CityMap cityMap, String outputFileName) {
        this.cityMap = cityMap;
        this.serviceDistributor = new ServiceDistributor();
        this.utilityDistributor = new UtilityDistributor();
        this.resourcePool = new ResourcePool();
        this.outputPrinter = new OutputPrinter(outputFileName);
    }

    public void run(int tickCount) {
        for (int tick = 1; tick <= tickCount; tick++) {
            outputPrinter.printTick(tick);

            resetZones();

            serviceDistributor.distributeServices(cityMap, outputPrinter);
            utilityDistributor.distributeUtilities(cityMap, outputPrinter);

            resourcePool.distributeResources(cityMap, outputPrinter);

            updateAndProduceAllZones();

            resourcePool.snapshot(cityMap);
        }

        outputPrinter.close();
    }

    private void resetZones() {
        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof Zone) {
                    ((Zone) cell).resetReceivedValues();
                }
            }
        }
    }

    private void updateAndProduceAllZones() {
        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof Zone) {
                    Zone zone = (Zone) cell;
                    int oldLevel = zone.getLevel();

                    zone.updateLevel();
                    zone.produce();

                    outputPrinter.printGenerated(zone);
                    outputPrinter.printLevelChange(zone, oldLevel);
                }
            }
        }
    }
}
