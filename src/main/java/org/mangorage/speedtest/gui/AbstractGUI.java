package org.mangorage.speedtest.gui;

import org.mangorage.speedtest.Main;
import org.mangorage.speedtest.core.Utils;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractGUI {
    protected abstract JLabel getLabel();
    public AbstractGUI() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                var tracker = Main.getDataTracker();
                if (tracker != null) {
                    SwingUtilities.invokeLater(() -> getLabel().setText("Data rate: %s/s".formatted(Utils.formatDataRate(tracker.getCurrentDataRate()))));
                }
            }
        }, 0, 250);
    }
}
