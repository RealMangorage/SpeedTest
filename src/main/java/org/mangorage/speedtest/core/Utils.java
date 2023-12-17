package org.mangorage.speedtest.core;

import java.util.Random;

public class Utils {
    public static final Random random = new Random();
    public static final int SIZE_PER_PACKET = 1024 * 1024; // 1024 bytes -> 1KB
    public static final boolean useBits = true;

    public static String formatDataRate(double dataRate) {
        if (dataRate < 1024) {
            return String.format("%.2f KB", dataRate);
        } else if (dataRate < 1048576) { // 1024 * 1024
            return String.format("%.2f MB", dataRate / 1024.0);
        } else if (dataRate < 1073741824) { // 1024 * 1024 * 1024
            return String.format("%.2f GB", dataRate / 1048576.0);
        } else {
            return String.format("%.2f TB", dataRate / 1073741824.0);
        }
    }

    public static byte[] generateRandomDataPacket(int size) {
        byte[] data = new byte[size];
        random.nextBytes(data);
        return data;
    }
}
