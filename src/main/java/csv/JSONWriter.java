package csv;

import carparkmodel.Sensor;

import java.io.*;

import static util.KafkaProducerAPI.runProducer;

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
        // REMOVE FOR LATER
        writeToFile(jsonString(sensor));
        writeToTerminal(jsonString(sensor));
    }

    private String jsonString(Sensor sensor) {
        return "{" +
                "\"timestamp\": \"" + sensor.getTimestamp() + "\"," +
                "\"nodeID\": \"" + sensor.getMacAddress() + "\"," +
                "\"payload\": {" +
                "\"occupied\": " + sensor.getIsOccupiedAsString() + "" +
                "}" +
                "}\n";
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

    public void writeToTerminal(String json) {
        System.out.println(json);
    }

    public void toKafkaProducer(final Sensor randomSensor, final String server, final String port, final String topicName) {
        final String bootstrapServer = server + ":" + port;

        final Thread thread = new Thread(new Runnable() {
            private volatile boolean running = true;

            public void run() {
                while (running) {
                    runProducer(bootstrapServer, topicName, jsonString(randomSensor));
                    running = false;
                }
            }
        });

        thread.start();
    }
}
