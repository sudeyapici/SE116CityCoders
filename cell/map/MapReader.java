package map;

import cell.Cell;
import cell.EmptyCell;
import cell.Road;
import cell.zone.HousingZone;
import cell.zone.IndustrialZone;
import cell.zone.CommercialZone;
import cell.provider.utility.PowerPlant;
import cell.provider.utility.WaterPumpingStation;
import cell.provider.utility.InternetHub;
import cell.provider.service.PoliceStation;
import cell.provider.service.Hospital;
import cell.provider.service.School;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MapReader {

    public CityMap readMap(String filePath) throws IOException, InvalidMapException {
        int rows = 0;
        int cols = 0;

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (rows == 0) {
                    cols = line.length();
                } else if (line.length() != cols) {
                    throw new InvalidMapException(
                            "Map rows must have the same length."
                    );
                }

                rows++;
            }

        }

        if (rows == 0 || cols == 0) {
            throw new InvalidMapException("Map file is empty.");
        }

        CityMap cityMap = new CityMap(rows, cols);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {

                for (int col = 0; col < line.length(); col++) {

                    char symbol = line.charAt(col);

                    Cell cell = createCell(symbol, row, col);

                    cityMap.setCell(row, col, cell);
                }

                row++;
            }
        }

        return cityMap;
    }


    private Cell createCell(char symbol, int row, int col) throws InvalidMapException {

        switch (symbol) {

            case 'H':
                return new HousingZone(row, col);

            case 'I':
                return new IndustrialZone(row, col);

            case 'C':
                return new CommercialZone(row, col);

            case 'R':
                return new Road(row, col);

            case 'E':
                return new EmptyCell(row, col);

            case 'P':
                return new PowerPlant(row, col);

            case 'W':
                return new WaterPumpingStation(row, col);

            case 'T':
                return new InternetHub(row, col);

            case 'F':
                return new PoliceStation(row, col);

            case 'D':
                return new Hospital(row, col);

            case 'S':
                return new School(row, col);

            default:
                throw new InvalidMapException("Unknown map symbol at row " + row + ", column " + col + ": " + symbol);
        }
    }
}

