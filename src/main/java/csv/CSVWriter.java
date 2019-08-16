package csv;

import carparkmodel.Sensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {

    private static final String FILE_EXTENSION = ".csv";
    private final String _filename;

    public CSVWriter(String filename) {
        _filename = filename + FILE_EXTENSION;

        try {
            File f = new File(filename);

            // Only if file doesn't exist, create a new csv file
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendNodeToFile(Sensor sensor) {
        try {
            FileWriter fileWriter = new FileWriter(_filename, true);

            String line =
                    // Sensor ID
                    sensor.getMacAddress() +
                    "," +
                    // Date
                    sensor.getTimestamp() +
                    "," +
                    // Occupancy
                    sensor.getIsOccupiedAsString() +
                    "\n";

            fileWriter.write(line);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
