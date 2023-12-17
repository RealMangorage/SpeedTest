package org.mangorage.speedtest.core;

import org.mangorage.speedtest.Main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static org.mangorage.speedtest.core.Utils.*;

public class ClientSpeedTest implements IDataRate {
    public static void main(String[] args) {
        Main.main(new String[]{"-client", "127.0.0.1:12345"});
    }
    private final String SERVER_IP;
    private final int SERVER_PORT;
    private long dataRate = 0;

    public ClientSpeedTest(String serverIp, int serverPort) {
        this.SERVER_IP = serverIp;
        this.SERVER_PORT = serverPort;
    }

    private void startTransmission() {
        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT); OutputStream output = socket.getOutputStream()) {

                byte[] dataPacket = generateRandomDataPacket(SIZE_PER_PACKET);

                long startTime = System.currentTimeMillis();
                long bytesSent = 0;

                while (true) {
                    output.write(dataPacket, 0, dataPacket.length);
                    bytesSent += dataPacket.length;

                    // Print data rate every second
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - startTime >= 1000) {
                        updateDataRate(bytesSent);
                        bytesSent = 0;
                        startTime = currentTime;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateDataRate(long bytesSent) {
        this.dataRate = (long) (bytesSent / (1024.0) / 1.0);

    }

    @Override
    public long getCurrentDataRate() {
        return dataRate;
    }

    @Override
    public void run() {
        startTransmission();
    }
}
