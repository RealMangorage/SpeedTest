package org.mangorage.speedtest.core;

import org.mangorage.speedtest.Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;

import static org.mangorage.speedtest.core.Utils.SIZE_PER_PACKET;

public class ServerSpeedTest implements IDataRate {

    public static void main(String[] args) {
        Main.main(new String[]{"-server", "127.0.0.1", "12345"});
    }

    private final String serverIP;
    private final int serverPort;
    private long startTime;
    private long bytesReceived;
    private long dataRate;

    public ServerSpeedTest(String serverIp, int serverPort) {
        this.serverIP = serverIp;
        this.serverPort = serverPort;

        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                System.out.println("Server is listening on port " + serverPort);

                while (true) {
                    try (var clientSocket = serverSocket.accept(); var dis = new DataInputStream(clientSocket.getInputStream())) {
                        System.out.println("Client connected");

                        startTime = System.currentTimeMillis();
                        bytesReceived = 0;

                        byte[] buffer = new byte[SIZE_PER_PACKET];
                        int bytesRead;

                        while ((bytesRead = dis.read(buffer)) != -1) {
                            bytesReceived += bytesRead;

                            // Print data rate every second
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - startTime >= 1000) {
                                long elapsedTime = currentTime - startTime;

                                if (elapsedTime > 0) {
                                    this.dataRate = (long) (bytesReceived / (1024.0) / (elapsedTime / 1000.0));
                                }

                                startTime = currentTime;
                                bytesReceived = 0;
                            }
                        }

                    } catch (IOException ignored) {
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public long getCurrentDataRate() {
        return dataRate;
    }

    @Override
    public void run() {

    }
}
