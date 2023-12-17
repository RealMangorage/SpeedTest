package org.mangorage.speedtest.core;

public interface IDataRate {
    long getCurrentDataRate(); // returns a long representing the amount of bytes sent/received in the last second
    void run();
}
