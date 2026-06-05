import map.CityMap;
import map.InvalidMapException;
import map.MapReader;
import simulation.Simulation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar CitySimulation.jar <map_file> <tick_count>");
            return;
        }

        String mapFile = args[0];
        int tickCount;

        try {
            tickCount = Integer.parseInt(args[1]);

            if (tickCount <= 0) {
                System.out.println("Tick count must be positive.");
                return;
            }

            MapReader mapReader = new MapReader();
            CityMap cityMap = mapReader.readMap(mapFile);

            Simulation simulation = new Simulation(cityMap, "output.txt");
            simulation.run(tickCount);

            System.out.println("Simulation completed. Output written to output.txt.");

        } catch (NumberFormatException e) {
            System.out.println("Tick count must be a number.");
        } catch (IOException e) {
            System.out.println("Map file could not be read.");
        } catch (InvalidMapException e) {
            System.out.println("Invalid map: " + e.getMessage());
        }
    }
}