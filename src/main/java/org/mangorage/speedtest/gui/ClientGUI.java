package org.mangorage.speedtest.gui;

import org.mangorage.speedtest.Main;
import org.mangorage.speedtest.core.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ClientGUI extends AbstractGUI {
    private JFrame frame;
    private JLabel dataRateLabel;

    public static void create() {
        SwingUtilities.invokeLater(ClientGUI::new);
    }

    @Override
    protected JLabel getLabel() {
        return dataRateLabel;
    }

    private ClientGUI() {
        frame = new JFrame("Client GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        dataRateLabel = new JLabel("Data rate: N/A");
        frame.getContentPane().add(BorderLayout.CENTER, dataRateLabel);

        JButton startButton = new JButton("Start Transmission");
        startButton.addActionListener(e -> {
            var dataTracker = Main.getDataTracker();
            if (dataTracker != null) dataTracker.run();
        });

        frame.setLayout(new BorderLayout());
        frame.add(dataRateLabel, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
