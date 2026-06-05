package simulation;

import cell.zone.CommercialZone;
import cell.zone.HousingZone;
import cell.zone.IndustrialZone;
import cell.zone.Zone;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputPrinter {
    private BufferedWriter writer;

    public OutputPrinter(String outputFileName) {
        try {
            this.writer = new BufferedWriter(new FileWriter(outputFileName));
        } catch (IOException e) {
            System.out.println("Output file could not be created.");
        }
    }

    public void printTick(int tick) {
        writeLine("Tick " + tick);
    }

    public void printServiceReceived(Zone zone, String serviceType) {
        writeLine(getZoneName(zone) + " at (" + zone.getRow() + "," + zone.getCol()
                + ") received " + serviceType + " service");
    }

    public void printUtilityReceived(Zone zone, String utilityType, int amount) {
        if (amount > 0) {
            writeLine(getZoneName(zone) + " at (" + zone.getRow() + "," + zone.getCol()
                    + ") received " + amount + " " + utilityType);
        }
    }

    public void printPopulationReceived(Zone zone, int amount) {
        if (amount > 0) {
            writeLine(getZoneName(zone) + " at (" + zone.getRow() + "," + zone.getCol()
                    + ") received " + amount + " population");
        }
    }

    public void printGoodsReceived(Zone zone, int amount) {
        if (amount > 0) {
            writeLine(getZoneName(zone) + " at (" + zone.getRow() + "," + zone.getCol()
                    + ") received " + amount + " goods");
        }
    }

    public void printLifestyleReceived(Zone zone, int amount) {
        if (amount > 0) {
            writeLine(getZoneName(zone) + " at (" + zone.getRow() + "," + zone.getCol()
                    + ") received " + amount + " lifestyle");
        }
    }

    public void printGenerated(Zone zone) {
        if (zone instanceof HousingZone) {
            HousingZone h = (HousingZone) zone;
            writeLine("House at (" + h.getRow() + "," + h.getCol() + ") generated "
                    + h.getProducedPopulation() + " population");
        } else if (zone instanceof IndustrialZone) {
            IndustrialZone i = (IndustrialZone) zone;
            writeLine("Industrial at (" + i.getRow() + "," + i.getCol() + ") generated "
                    + i.getProducedGoods() + " goods");
        } else if (zone instanceof CommercialZone) {
            CommercialZone c = (CommercialZone) zone;
            writeLine("Commercial at (" + c.getRow() + "," + c.getCol() + ") generated "
                    + c.getProducedLifestyle() + " lifestyle");
        }
    }

    public void printLevelChange(Zone zone, int oldLevel) {
        int newLevel = zone.getLevel();

        if (newLevel > oldLevel) {
            writeLine(getZoneName(zone) + " at (" + zone.getRow() + "," + zone.getCol()
                    + ") levels up from " + oldLevel + " to " + newLevel);
        } else if (newLevel < oldLevel) {
            writeLine(getZoneName(zone) + " at (" + zone.getRow() + "," + zone.getCol()
                    + ") levels down from " + oldLevel + " to " + newLevel);
        }
    }

    private String getZoneName(Zone zone) {
        if (zone instanceof HousingZone) {
            return "House";
        } else if (zone instanceof IndustrialZone) {
            return "Industrial";
        } else if (zone instanceof CommercialZone) {
            return "Commercial";
        }

        return "Zone";
    }

    private void writeLine(String text) {
        try {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Output could not be written.");
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Output file could not be closed.");
        }
    }
}
