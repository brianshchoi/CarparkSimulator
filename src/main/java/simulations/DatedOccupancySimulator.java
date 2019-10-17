package simulations;

import carparkmodel.CarPark;
import carparkmodel.Sensor;
import csv.JSONWriter;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This simulator is used for generating random occupancy datasets using past dates (NOT EVENT-DRIVEN)
 */
public class DatedOccupancySimulator {

    public static void main(String[] args) {
        System.out.println("Occupancy Simulator Dated running");
        if (args.length != 12) {

            System.out.println("Invalid Arguments");
            System.out.println("Arg 0: Bootstrap servers e.g. \"localhost:9092\" or \"lpc01-kafka01:9092,lpc01-kafka01:9093,lpc01-kafka02:9092\"");
            System.out.println("Arg 1: Topic name e.g. \"sp-topic\" or \"sp-occupancy-1\"");
            System.out.println("Arg 2: Carpark ID");
            System.out.println("Arg 3: Carpark Capacity");
            System.out.println("Arg 4: Number of Runs");
            System.out.println("Arg 5: Delay in minutes between Simulations");
            System.out.println("Arg 6: Starting Day");
            System.out.println("Arg 7: Starting Month");
            System.out.println("Arg 8: Starting Year");
            System.out.println("Arg 9: Starting Hour (24h)");
            System.out.println("Arg 10: Starting Minute");
            System.out.println("Arg 11: Starting Second");

            System.exit(0);
        } else {
            try {

                String bootstrapServer = args[0];
                String topicName = args[1];
                int carParkID = Integer.parseInt(args[2]);
                int carParkCapacity = Integer.parseInt(args[3]);
                int numberOfRuns = Integer.parseInt(args[4]);
                int delayBetweenMinutes = Integer.parseInt(args[5]);
                int day = Integer.parseInt(args[6]);
                int month = Integer.parseInt(args[7]);
                int year = Integer.parseInt(args[8]);
                int startingHour = Integer.parseInt(args[9]);
                int startingMinute = Integer.parseInt(args[10]);
                int startingSecond = Integer.parseInt(args[10]);

                // Valid argument check
                if (carParkID < 0 | carParkCapacity < 1 | numberOfRuns < 1 | delayBetweenMinutes < 1
                        | startingHour < 0 | startingHour > 23 | startingMinute < 0 | startingMinute > 59
                        | startingSecond < 0 | startingSecond > 59) {
                    throw new IllegalArgumentException("Argument passed has an invalid value");
                }

                Calendar calendar = new GregorianCalendar(
                        year, month - 1, day, startingHour, startingMinute, startingSecond);


                CarPark cp = MockOccupancy.randomiseCarPark(
                        new CarPark(carParkID, carParkCapacity, calendar), 1, numberOfRuns);

                JSONWriter jsonWriter = new JSONWriter();

                for (int i = 0; i < numberOfRuns; i++) {
                    for (int j = 0; j < carParkCapacity; j++) {
                        String macAddress = cp.getSensorIndexMap()[j];
                        Sensor sensor = cp.getSensor(macAddress);

//                        System.out.println(sensor.getMacAddress() + " " + sensor.getIsOccupied() + " " + sensor.getTimestamp());
                        jsonWriter.toKafkaProducer(sensor, bootstrapServer, topicName);
                    }

                    System.out.println("Data @" + cp.getTimestampAsString() + " appended");

                    // OPTIONAL
                    try {
                        // CAN CHANGE DELAY TO BE 0 IF YOU WANT THE DATA TO BE GENERATED INSTANTLY
                        // I just put in a delay so that kafka broker is not overwhelmed with data
                        Thread.sleep(delayBetweenMinutes * 100);
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
