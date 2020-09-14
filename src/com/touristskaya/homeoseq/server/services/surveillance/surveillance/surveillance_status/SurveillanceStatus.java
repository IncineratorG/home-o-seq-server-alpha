package com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_status;

import com.touristskaya.homeoseq.server.services.surveillance.surveillance.Surveillance;

public class SurveillanceStatus {
    private long timestamp;
    private boolean isRunning;
    private String testStringValue;

    public SurveillanceStatus() {
        timestamp = 0;
        isRunning = false;
        testStringValue = "";
    }

    public SurveillanceStatus(long timestamp,
                              boolean isRunning,
                              String testStringValue) {
        this.timestamp = timestamp;
        this.isRunning = isRunning;
        this.testStringValue = testStringValue;
    }

    public SurveillanceStatus(SurveillanceStatus other) {
        timestamp = other.timestamp;
        isRunning = other.isRunning;
        testStringValue = other.testStringValue;
    }

    public void setTimestamp(long value) {
        this.timestamp = value;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setIsRunning(boolean value) {
        this.isRunning = value;
    }
    public boolean getIsRunning() {
        return isRunning;
    }

    public void setTestStringValue(String value) {
        this.testStringValue = value;
    }
    public String getTestStringValue() {
        return testStringValue;
    }
}
