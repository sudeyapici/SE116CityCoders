package simulation;

import cell.Cell;
import cell.zone.CommercialZone;
import cell.zone.HousingZone;
import cell.zone.IndustrialZone;
import map.CityMap;

public class ResourcePool {
    private int totalPopulation;
    private int totalGoods;
    private int totalLifestyle;

    public ResourcePool() {
        this.totalPopulation = 0;
        this.totalGoods = 0;
        this.totalLifestyle = 0;
    }

    public void snapshot(CityMap cityMap) {
        totalPopulation = 0;
        totalGoods = 0;
        totalLifestyle = 0;

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof HousingZone) {
                    totalPopulation += ((HousingZone) cell).getProducedPopulation();
                } else if (cell instanceof IndustrialZone) {
                    totalGoods += ((IndustrialZone) cell).getProducedGoods();
                } else if (cell instanceof CommercialZone) {
                    totalLifestyle += ((CommercialZone) cell).getProducedLifestyle();
                }
            }
        }
    }

    public void distributeResources(CityMap cityMap, OutputPrinter outputPrinter) {
        distributePopulation(cityMap, outputPrinter);
        distributeGoods(cityMap, outputPrinter);
        distributeLifestyle(cityMap, outputPrinter);
    }

    private void distributePopulation(CityMap cityMap, OutputPrinter outputPrinter) {
        int targetCount = countPopulationTargets(cityMap);
        int amountPerZone = targetCount == 0 ? 0 : totalPopulation / targetCount;

        if (amountPerZone <= 0) {
            return;
        }

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof IndustrialZone) {
                    IndustrialZone zone = (IndustrialZone) cell;
                    zone.receivePopulation(amountPerZone);
                    outputPrinter.printPopulationReceived(zone, amountPerZone);

                } else if (cell instanceof CommercialZone) {
                    CommercialZone zone = (CommercialZone) cell;
                    zone.receivePopulation(amountPerZone);
                    outputPrinter.printPopulationReceived(zone, amountPerZone);
                }
            }
        }
    }

    private void distributeGoods(CityMap cityMap, OutputPrinter outputPrinter) {
        int targetCount = countGoodsTargets(cityMap);
        int amountPerZone = targetCount == 0 ? 0 : totalGoods / targetCount;

        if (amountPerZone <= 0) {
            return;
        }

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof CommercialZone) {
                    CommercialZone zone = (CommercialZone) cell;
                    zone.receiveGoods(amountPerZone);
                    outputPrinter.printGoodsReceived(zone, amountPerZone);
                }
            }
        }
    }

    private void distributeLifestyle(CityMap cityMap, OutputPrinter outputPrinter) {
        int targetCount = countLifestyleTargets(cityMap);
        int amountPerZone = targetCount == 0 ? 0 : totalLifestyle / targetCount;

        if (amountPerZone <= 0) {
            return;
        }

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof HousingZone) {
                    HousingZone zone = (HousingZone) cell;
                    zone.receiveLifestyle(amountPerZone);
                    outputPrinter.printLifestyleReceived(zone, amountPerZone);
                }
            }
        }
    }

    private int countPopulationTargets(CityMap cityMap) {
        int count = 0;

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                Cell cell = cityMap.getCell(row, col);

                if (cell instanceof IndustrialZone || cell instanceof CommercialZone) {
                    count++;
                }
            }
        }

        return count;
    }

    private int countGoodsTargets(CityMap cityMap) {
        int count = 0;

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                if (cityMap.getCell(row, col) instanceof CommercialZone) {
                    count++;
                }
            }
        }

        return count;
    }

    private int countLifestyleTargets(CityMap cityMap) {
        int count = 0;

        for (int row = 0; row < cityMap.getRows(); row++) {
            for (int col = 0; col < cityMap.getCols(); col++) {
                if (cityMap.getCell(row, col) instanceof HousingZone) {
                    count++;
                }
            }
        }

        return count;
    }
}
