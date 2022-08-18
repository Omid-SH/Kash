package com.example.nobintest.timeUtils;


public class TimeDiffLabel {

    private TimeScale timeScale;
    private int number;

    public TimeDiffLabel(TimeScale timeScale, int number) {
        this.timeScale = timeScale;
        this.number = number;
    }

    public String getTimeScale() {
        return timeScale.name();
    }

    public int getNumber() {
        return number;
    }

}