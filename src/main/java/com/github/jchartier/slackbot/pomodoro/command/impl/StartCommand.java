package com.github.jchartier.slackbot.pomodoro.command.impl;

import com.github.jchartier.slackbot.pomodoro.command.PomodoroCommand;
import com.github.jchartier.slackbot.pomodoro.exception.GenericCommandException;
import com.github.jchartier.slackbot.pomodoro.exception.InvalidStartDelayException;
import com.github.jchartier.slackbot.pomodoro.service.PomodoroNotificationService;
import com.ullink.slack.simpleslackapi.SlackUser;

import java.util.concurrent.TimeUnit;

public class StartCommand implements PomodoroCommand {

    private PomodoroNotificationService pomodoroNotificationService;

    public StartCommand(PomodoroNotificationService pomodoroNotificationService) {

        this.pomodoroNotificationService = pomodoroNotificationService;
    }

    @Override
    public void execute(SlackUser slackUser, String message) throws GenericCommandException {

        Integer delay = getNotificationDelay(message);
        pomodoroNotificationService.startPomodoro(slackUser.getUserName(), delay.longValue(), TimeUnit.MINUTES);
    }

    private Integer getNotificationDelay(String message) throws InvalidStartDelayException {

        try {

            return Integer.valueOf(message.split("\\s")[2]);
        } catch (Exception e) {

            throw new InvalidStartDelayException();
        }
    }
}