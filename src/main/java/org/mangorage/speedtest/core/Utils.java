package org.mangorage.speedtest.core;

import java.util.Random;

public class Utils {
    public static final Random random = new Random();
    public static final int SIZE_PER_PACKET = 1024 * 1024; // 1024 bytes -> 1KB
    public static final boolean useBits = true;

    public static String formatDataSize(double size) {
        return formatDataSize(size, false);
    }

    public static String formatDataSize(double size, boolean useBits) {
        if (useBits) {
            double bitsSize = size * 8.0; // Convert from bytes to bits
            if (bitsSize < 1024) {
                return String.format("%.2f %s", bitsSize, "bits");
            } else if (bitsSize < 1048576) { // 1024 * 1024
                return String.format("%.2f %s", bitsSize / 1024.0, "Kbits");
            } else if (bitsSize < 1073741824) { // 1024 * 1024 * 1024
                return String.format("%.2f %s", bitsSize / 1048576.0, "Mbits");
            } else {
                return String.format("%.2f %s", bitsSize / 1073741824.0, "Gbits");
            }
        } else {
            if (size < 1024) {
                return String.format("%.2f %s", size, "B");
            } else if (size < 1048576) { // 1024 * 1024
                return String.format("%.2f %s", size / 1024.0, "KB");
            } else if (size < 1073741824) { // 1024 * 1024 * 1024
                return String.format("%.2f %s", size / 1048576.0, "MB");
            } else {
                return String.format("%.2f %s", size / 1073741824.0, "GB");
            }
        }
    }

    public static byte[] generateRandomDataPacket(int size) {
        byte[] data = new byte[size];
        random.nextBytes(data);
        return data;
    }
}
