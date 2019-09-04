package simulations;

import carparkmodel.CarPark;
import carparkmodel.Sensor;
import csv.CSVWriter;
import csv.JSONWriter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Simulator {

    public static void main(String[] args) {
        String filename = "Carpark";

        if (args.length < 10) {
            System.out.println(
                    "Invalid amount of arguments \n" +
                            "Arg 0: CarPark ID \n" +
                            "Arg 1: CarPark Capacity \n" +
                            "Arg 2: Number of runs \n" +
                            "Arg 3: Delay in minutes between Simulations \n" +
                            "Arg 4: Starting Day \n" +
                            "Arg 5: Starting Month \n" +
                            "Arg 6: Starting Year \n" +
                            "Arg 7: Starting Hour (24h) \n" +
                            "Arg 8: Starting Minute \n" +
                            "Arg 9: Starting Second \n" +
                            "\n" + "e.g. java -jar CarparkSimulator.jar 1 100 288 5 12 30 0"
            );
            System.exit(0);
        } else {
            try {
                // Arguments
                int carParkID = Integer.parseInt(args[0]);
                int carParkCapacity = Integer.parseInt(args[1]);
                int numberOfRuns = Integer.parseInt(args[2]);
                int delayBetweenMinutes = Integer.parseInt(args[3]);
                int day = Integer.parseInt(args[4]);
                int month = Integer.parseInt(args[5]);
                int year = Integer.parseInt(args[6]);;
                int startingHour = Integer.parseInt(args[7]);
                int startingMinute = Integer.parseInt(args[8]);
                int startingSecond = Integer.parseInt(args[9]);

                // Valid argument check
                if (carParkID < 0 | carParkCapacity < 1 | numberOfRuns < 1 | delayBetweenMinutes < 1
                        | startingHour < 0 | startingHour > 23 | startingMinute < 0 | startingMinute > 59
                        | startingSecond < 0 | startingSecond > 59) {
                    throw new IllegalArgumentException("Argument passed has an invalid value");
                }

                Calendar calendar = new GregorianCalendar(
                        year, month-1, day, startingHour, startingMinute, startingSecond);

//                CSVWriter csvWriter = new CSVWriter(filename);
                JSONWriter jsonWriter = new JSONWriter(filename);

                System.out.println("CarPark Simulator running...");

                CarPark cp = MockOccupancy.randomiseCarPark(
                        new CarPark(carParkID, carParkCapacity, calendar), 1, numberOfRuns);

                for (int i = 0; i < numberOfRuns - 1; i++) {
                    HashMap<Integer, String> indexMap = cp.getSensorIndexMap();
                    HashMap<String, Sensor> sensors = cp.getSensors();

                    for (int j = 0; j < cp.getCapacity(); j++) {
                        String macAddress = indexMap.get(j);
                        jsonWriter.toJson(sensors.get(macAddress));
//                        csvWriter.appendNodeToFile(sensors.get(macAddress));
                    }

                    System.out.println("Data @" + cp.getTimestampAsString() + " appended");

                    try {
                        Thread.sleep(delayBetweenMinutes * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    calendar.add(Calendar.MINUTE, delayBetweenMinutes);
                    cp = MockOccupancy.randomiseCarPark(cp, i + 2, numberOfRuns);
                }
                System.out.println("Simulation Done");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
