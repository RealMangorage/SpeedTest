package org.mangorage.speedtest;

import org.mangorage.speedtest.core.ClientSpeedTest;
import org.mangorage.speedtest.core.IDataRate;
import org.mangorage.speedtest.core.ServerSpeedTest;
import org.mangorage.speedtest.gui.ClientGUI;
import org.mangorage.speedtest.gui.ServerGUI;

public class Main {
    private static IDataRate dataTracker;

    public static IDataRate getDataTracker() {
        return dataTracker;
    }


    public static void main(String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("-server")) {
                new Thread(ServerGUI::create).start();
                dataTracker = new ServerSpeedTest("127.0.0.1", 12345);
            } else if (args[0].equals("-client")) {
                if (args.length >= 2) {
                    if (!args[1].contains(":")) return;
                    String[] serverInfo = args[1].split(":");
                    var SERVER_IP = serverInfo[0];
                    var SERVER_PORT = Integer.parseInt(serverInfo[1]);

                    new Thread(ClientGUI::create).start();
                    dataTracker = new ClientSpeedTest(SERVER_IP, SERVER_PORT);
                }
            }
        }
    }
}
