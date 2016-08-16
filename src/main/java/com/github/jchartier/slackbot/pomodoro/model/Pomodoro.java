package com.github.jchartier.slackbot.pomodoro.model;

public class Pomodoro {

    private String username;
    private long currentDelay;

    public Pomodoro(String username, long currentDelay) {
        this.username = username;
        this.currentDelay = currentDelay;
    }

    public String getUsername() {
        return username;
    }

    public long getCurrentDelay() {
        return currentDelay;
    }
}
