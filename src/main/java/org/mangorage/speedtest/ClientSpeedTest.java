package org.mangorage.speedtest;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static org.mangorage.speedtest.Utils.*;

public class ClientSpeedTest {
    private static String SERVER_IP = "10.0.0.103";
    private static int SERVER_PORT = 12345;

    private JFrame frame;
    private JLabel dataRateLabel;

    public static void main(String[] args) {
        if (args.length >= 2) {
            if (!args[1].contains(":")) return;
            String[] serverInfo = args[1].split(":");
            SERVER_IP = serverInfo[0];
            SERVER_PORT = Integer.parseInt(serverInfo[1]);
        }


        SwingUtilities.invokeLater(() -> {
            try {
                new ClientSpeedTest().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void start() throws IOException {
        frame = new JFrame("Client GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        dataRateLabel = new JLabel("Data rate: 0.00 KB/s");
        frame.getContentPane().add(BorderLayout.CENTER, dataRateLabel);

        JButton startButton = new JButton("Start Transmission");
        startButton.addActionListener(e -> startTransmission());

        frame.setLayout(new BorderLayout());
        frame.add(dataRateLabel, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);

        frame.setVisible(true);
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
        double dataRate = (double) bytesSent / (1024.0) / 1.0;
        String formattedDataRate = formatDataRate(dataRate);
        dataRateLabel.setText("Data rate: " + formattedDataRate + "/s");
    }
}
