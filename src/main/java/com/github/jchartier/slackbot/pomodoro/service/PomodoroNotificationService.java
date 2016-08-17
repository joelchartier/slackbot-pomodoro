package com.github.jchartier.slackbot.pomodoro.service;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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

    private String getStartPomodoroMessage(String username, long delay, TimeUnit timeUnit) {

        return String.format(" >>> %s, I started your pomodoro! \n" +
                        " Type `help` if you need anything. \n" +
                        " *Have a productive %s %s!*",
                username, delay, timeUnit.toString());
    }

    public void stopPomodoro(SlackUser slackUser) {

        String userName = slackUser.getUserName();

        if (!pomodoroService.pomodoroExists(userName)) {
            slackSession.sendMessageToUser(slackUser, ">>> You don't have any pomodoro running", null);
        } else {
            pomodoroService.delete(userName);
            slackSession.sendMessageToUser(slackUser, ">>> Your pomodoro was stopped", null);
        }
    }
}