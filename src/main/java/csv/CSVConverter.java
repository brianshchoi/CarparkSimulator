package csv;

import carparkmodel.CarPark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVConverter {

    private static final String FILE_EXTENSION = ".txt";
    //    private static final String FOLDER = "./dataset/";
    private final String _filename;

    public CSVConverter(String filename) {
        _filename = filename + FILE_EXTENSION;

        try {
            initialiseFile(_filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    - Initialise File creates the log directory if it doesn't exist
    - It also creates the txt file if it doesn't exist with headers.
     */
    private void initialiseFile(String filename) throws IOException {
        File f = new File(filename);
//        if (!f.getParentFile().exists()) {
//            f.getParentFile().mkdirs();
//        }
        if (!f.exists()) {
            f.createNewFile();

            FileWriter fileWriter = new FileWriter(_filename, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            List<String[]> data = new ArrayList<>();
            // Create Headers
            data.add(new String[]{"CARPARK_ID", "SENSOR_ID", "DATE", "OCCUPIED", "BATTERY_HEALTH", "NODE_STATUS"});

            csvWriter.writeAll(data);
        }
    }

    public void appendDataToFile(CarPark cp) {
        try {
            char comma = ',';
            char empty = ' ';

            FileWriter fileWriter = new FileWriter(_filename, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter, comma, empty);

            List<String[]> data = new ArrayList<>();

            // DataSet Row
            for (Sensor sensor : cp.getSensors()) {
                data.add(new String[] {
                        // CarPark Id
                        "Carpark" + "0" + Integer.toString(cp.getCarParkID()),

                        // Sensor Id
                        Integer.toString(sensor.getId()),

                        // Date
                        sensor.getTimestamp(),

                        // Occupancy
                        sensor.getIsOccupiedAsString(),

                        // Battery Health
                        sensor.getBatteryHealth(),

                        // Node Status
                        sensor.getNodeStatus()
                });
            }
            csvWriter.writeAll(data);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
