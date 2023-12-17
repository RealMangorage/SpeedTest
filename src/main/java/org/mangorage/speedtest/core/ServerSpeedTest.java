package org.mangorage.speedtest.core;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mangorage.speedtest.core.Utils.SIZE_PER_PACKET;
import static org.mangorage.speedtest.core.Utils.formatDataRate;

public class ServerSpeedTest {
    private static final int PORT = 12345;

    private JFrame frame;
    private JLabel dataRateLabel;

    private long startTime;
    private long bytesReceived;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ServerSpeedTest().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void start() throws IOException {
        frame = new JFrame("Server GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        dataRateLabel = new JLabel("Data rate: 0.00 KB/s");
        frame.getContentPane().add(BorderLayout.CENTER, dataRateLabel);

        frame.setVisible(true);

        new Thread(() -> {
            try {
                startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                SwingUtilities.invokeLater(() -> dataRateLabel.setText("Data rate: No Clients"));
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
                            SwingUtilities.invokeLater(this::updateDataRate);
                        }
                    }

                } catch (IOException ignored) {}
            }
        } finally {
            SwingUtilities.invokeLater(() -> frame.dispose()); // Close the GUI when the server stops
        }
    }

    private void updateDataRate() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (elapsedTime > 0) {
            double dataRate = (double) bytesReceived / (1024.0) / (elapsedTime / 1000.0);
            dataRateLabel.setText("Data rate: %s/s".formatted(formatDataRate(dataRate)));
        }

        startTime = currentTime;
        bytesReceived = 0;
    }
}
