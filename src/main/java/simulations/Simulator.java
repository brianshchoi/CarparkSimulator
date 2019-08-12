package simulations;

import carparkmodel.CarPark;
import csv.CSVConverter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Simulator {

    public static void main(String[] args) {
        String filename = "Carpark";

        if (args.length < 7) {
            System.out.println(
                    "Invalid amount of arguments \n" +
                            "Arg 0: CarPark ID \n" +
                            "Arg 1: CarPark Capacity \n" +
                            "Arg 2: Number of runs \n" +
                            "Arg 3: Delay in minutes between Simulations \n" +
                            "Arg 4: Starting Hour (24h) \n" +
                            "Arg 5: Starting Minute \n" +
                            "Arg 6: Starting Second \n" +
                            "\n" + "e.g. java -jar SmartParkingSpark.jar 1 100 288 5 12 30 0"
            );
            System.exit(0);
        } else {
            try {
                CarPark cp;

                // Arguments
                int carParkID = Integer.parseInt(args[0]);
                int carParkCapacity = Integer.parseInt(args[1]);
                int numberOfRuns = Integer.parseInt(args[2]);
                int delayBetweenMinutes = Integer.parseInt(args[3]);
                int startingHour = Integer.parseInt(args[4]);
                int startingMinute = Integer.parseInt(args[5]);
                int startingSecond = Integer.parseInt(args[6]);

                // Valid argument check
                if (carParkID < 0 | carParkCapacity < 1 | numberOfRuns < 1 | delayBetweenMinutes < 1
                        | startingHour < 0 | startingHour > 23 | startingMinute < 0 | startingMinute > 59
                        | startingSecond < 0 | startingSecond > 59) {
                    throw new IllegalArgumentException("Argument passed has an invalid value");
                }

                Calendar calendar = GregorianCalendar.getInstance();
                calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        startingHour,
                        startingMinute,
                        startingSecond);

                CSVConverter csvConverter = new CSVConverter(filename);

                System.out.println("CarPark Simulator running...");

                for (int i = 0; i < numberOfRuns; i++) {
                    cp = MockOccupancy.randomiseCarPark(
                            new CarPark(carParkID, carParkCapacity, calendar), i + 1, numberOfRuns);
                    csvConverter.appendDataToFile(cp);

                    System.out.println("Data @" + cp.getTimestampAsString() + " appended");

                    try {
                        Thread.sleep(delayBetweenMinutes * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    calendar.add(Calendar.MINUTE, delayBetweenMinutes);
                }
                System.out.println("Simulation Done");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
