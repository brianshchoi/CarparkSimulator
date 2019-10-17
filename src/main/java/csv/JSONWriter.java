package csv;

import carparkmodel.Sensor;

import java.io.*;

import static util.KafkaProducerAPI.runProducer;

public class JSONWriter {

    private static final String FILE_EXTENSION = ".json";
    private final String _filename;

    public JSONWriter() {
        _filename = "carpark";

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
//        writeToFile(sensorJsonString(sensor));
        writeToTerminal(sensorJsonString(sensor));
    }

    private String sensorJsonString(Sensor sensor) {
        return "{" +
                "\"timestamp\": \"" + sensor.getTimestamp() + "\"," +
                "\"nodeID\": \"" + sensor.getMacAddress() + "\"," +
                "\"payload\": {" +
                "\"occupied\": " + sensor.getIsOccupiedAsString() + "" +
                "}" +
                "}\n";
    }

    private String gateJsonString(String[] fields) {
        return "{" +
                "\"timestamp\": \"" + fields[0] + "\"," +
                "\"nodeID\": \"" + fields[1] + "\"," +
                "\"payload\": {" +
                "\"occupied\": " + fields[2] + "" +
                "}" +
                "}\n";
    }

    // Not needed anymore, but writes to the filename that is provided
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

    // To Kafka Producer for Occupancy Simulator
    public void toKafkaProducer(final Sensor randomSensor, final String bootstrapServer, final String topicName) {
        final Thread thread = new Thread(new Runnable() {
            private volatile boolean running = true;

            public void run() {
                while (running) {
                    runProducer(bootstrapServer, topicName, sensorJsonString(randomSensor));
                    running = false;
                }
            }
        });

        thread.start();
    }

    // To Kafka Producer for Gate Driven Simulator
    public void toKafkaProducer(final String[] fields, final String bootstrapServer, final String topicName) {
        final Thread thread = new Thread(new Runnable() {
            private volatile boolean running = true;

            public void run() {
                while (running) {
                    runProducer(bootstrapServer, topicName, gateJsonString(fields));
                    running = false;
                }
            }
        });

        thread.start();
    }
}
