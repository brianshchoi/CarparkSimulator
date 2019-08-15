package util;

import java.util.Random;

public class MacAddress {

    public static String generateRandomMacAddress() {
        String macAddress = generateSubString();

        for (int i = 0; i < 5; i++) {
            macAddress = macAddress.concat(":");
            macAddress = macAddress.concat(generateSubString());
        }

        return macAddress;
    }

    private static String generateSubString() {
        Random random = new Random();

        int maxValue = 0xFF;
        int randomValue = random.nextInt(maxValue);

        if (randomValue < 0x10) {
            return "0" + Integer.toHexString(randomValue);
        } else {
            return Integer.toHexString(randomValue);
        }
    }
}
