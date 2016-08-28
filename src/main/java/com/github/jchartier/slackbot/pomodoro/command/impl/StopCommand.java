package com.github.jchartier.slackbot.pomodoro.command.impl;

import com.github.jchartier.slackbot.pomodoro.command.PomodoroCommand;
import com.github.jchartier.slackbot.pomodoro.service.PomodoroNotificationService;
import com.ullink.slack.simpleslackapi.SlackUser;

public class StopCommand implements PomodoroCommand {

    private PomodoroNotificationService pomodoroNotificationService;

    public StopCommand(PomodoroNotificationService pomodoroNotificationService) {

        this.pomodoroNotificationService = pomodoroNotificationService;
    }

    @Override
    public void execute(SlackUser slackUser, String message) {

        pomodoroNotificationService.stopPomodoro(slackUser);
    }
}
