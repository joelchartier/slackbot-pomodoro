package com.github.jchartier.slackbot.pomodoro.notifier;

import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CompletedPomodoroNotifier {

    @Autowired
    private SlackSession slackSession;

    @Autowired
    private MessageSource messageSource;

    public void notifyUser(String user) {

        String message = messageSource.getMessage("pomodoro.is.completed", null, Locale.ENGLISH);
        slackSession.sendMessageToUser(user, message, null);
    }
}