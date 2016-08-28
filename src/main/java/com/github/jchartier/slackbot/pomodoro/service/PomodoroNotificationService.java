package com.github.jchartier.slackbot.pomodoro.service;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class PomodoroNotificationService {

    @Autowired
    private PomodoroService pomodoroService;

    @Autowired
    private SlackSession slackSession;

    @Autowired
    private MessageSource messageSource;

    public void startPomodoro(String username, long delay, TimeUnit timeUnit) {

        pomodoroService.create(username, delay, timeUnit);
        slackSession.sendMessageToUser(username, getStartPomodoroMessage(username, delay, timeUnit), null);
    }

    private String getStartPomodoroMessage(String username, long delay, TimeUnit timeUnit) {

        return messageSource.getMessage("command.start", new Object[]{username, delay, timeUnit.toString()}, Locale.ENGLISH);
    }

    public void stopPomodoro(SlackUser slackUser) {

        String userName = slackUser.getUserName();

        if (!pomodoroService.pomodoroExists(userName)) {

            slackSession.sendMessageToUser(slackUser,
                    messageSource.getMessage("pomodoro.none.active", null, Locale.ENGLISH), null);
        } else {

            pomodoroService.delete(userName);
            slackSession.sendMessageToUser(slackUser,
                    messageSource.getMessage("command.stop", null, Locale.ENGLISH), null);
        }
    }
}