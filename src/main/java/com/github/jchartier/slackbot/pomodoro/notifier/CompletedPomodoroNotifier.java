package com.github.jchartier.slackbot.pomodoro.notifier;

import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompletedPomodoroNotifier {

    @Autowired
    private SlackSession slackSession;

    public void notifyUser(String user) {

        slackSession.sendMessageToUser(user, "Your pomodoro is completed", null);
    }
}