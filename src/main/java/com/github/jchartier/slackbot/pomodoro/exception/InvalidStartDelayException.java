package com.github.jchartier.slackbot.pomodoro.exception;

public class InvalidStartDelayException extends GenericCommandException {

    public InvalidStartDelayException() {

        super("Your pomodoro delay must be a valid number between 1 and 999");
    }
}