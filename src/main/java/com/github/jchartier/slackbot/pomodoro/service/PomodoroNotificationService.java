package com.github.jchartier.slackbot.pomodoro.service;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class PomodoroNotificationService {

    @Autowired
    private PomodoroService pomodoroService;

    @Autowired
    private SlackSession slackSession;

    public void startPomodoro(String username, long delay, TimeUnit timeUnit) {

        pomodoroService.create(username, delay, timeUnit);
        slackSession.sendMessageToUser(username, getStartPomodoroMessage(username, delay, timeUnit), null);
    }

    public void stopPomodoro(SlackUser slackUser) {

        pomodoroService.delete(slackUser.getUserName());
        slackSession.sendMessageToUser(slackUser, "You pomodoro was stopped", null);
    }

    private String getStartPomodoroMessage(String username, long delay, TimeUnit timeUnit) {

        return String.format("%s, " +
                "your pomodoro of %s %s has been started.\n" +
                "You will be notified when it's completed.\n" +
                "You can also stop your pomodoro by using the command 'stop pomodoro'", username, delay, timeUnit.toString());
    }
}