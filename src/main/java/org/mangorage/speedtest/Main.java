package org.mangorage.speedtest;

import org.mangorage.speedtest.core.ClientSpeedTest;
import org.mangorage.speedtest.core.ServerSpeedTest;

public class Main {
    public static void main(String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("-server")) {
                ServerSpeedTest.main(args);
            } else if (args[0].equals("-client")) {
                ClientSpeedTest.main(args);
            }
        }
    }
}
