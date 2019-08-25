package csv;

import carparkmodel.Sensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {

    private static final String FILE_EXTENSION = ".json";
    private final String _filename;

    public JSONWriter(String filename) {
        _filename = filename + FILE_EXTENSION;

        try {
            File f = new File(_filename);

            // Only if file doesn't exist, create a new csv file
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toJson(Sensor sensor) {

        String json =
                "{" +
                "\"timestamp\": \"" + sensor.getTimestamp() + "\"," +
                "\"nodeID\": \"" + sensor.getMacAddress() + "\"," +
                "\"payload\": {" +
                "\"occupied\": " + sensor.getIsOccupiedAsString() + "" +
                "}" +
                "}\n";

        // REMOVE FOR LATER
        writeToFile(json);

        //
        writeToTerminal(json);
    }

    private void writeToFile(String json) {
        try {
            FileWriter fileWriter = new FileWriter(_filename, true);
            fileWriter.write(json);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToTerminal(String json) {
        System.out.println(json);
    }

}
