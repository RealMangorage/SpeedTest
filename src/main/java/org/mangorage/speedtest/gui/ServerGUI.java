package org.mangorage.speedtest.gui;


import javax.swing.*;
import java.awt.*;

public class ServerGUI extends AbstractGUI {
    private JFrame frame;
    private JLabel dataRateLabel;


    public static void create() {
        SwingUtilities.invokeLater(ServerGUI::new);
    }

    @Override
    protected JLabel getLabel() {
        return dataRateLabel;
    }

    private ServerGUI() {
        frame = new JFrame("Server GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        dataRateLabel = new JLabel("Data rate: N/A");
        frame.getContentPane().add(BorderLayout.CENTER, dataRateLabel);

        frame.setVisible(true);
    }
}
