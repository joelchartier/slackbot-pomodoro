package com.github.jchartier.slackbot.pomodoro;

import com.github.jchartier.slackbot.pomodoro.service.PomodoroNotificationService;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Component
public class PomodoroCommandManager {

    private static final Pattern startPattern = Pattern.compile("start pomodoro [0-9]{1,3}", Pattern.CASE_INSENSITIVE);

    @Autowired
    private SlackSession slackSession;

    @Autowired
    private PomodoroNotificationService pomodoroNotificationService;

    @PostConstruct
    public void register() {

        SlackMessagePostedListener messagePostedListener = (event, session) -> {

            String message = event.getMessageContent();
            SlackUser slackUser = event.getSender();

            if (isNotInstantMessaging(event)) {

                return;
            }

            if (isStartCommand(message)) {

                startPomodoro(message, slackUser);
                return;
            }

            if (isStopCommand(message)) {

                stopPomodoro(slackUser);
                return;
            }

            slackSession.sendMessageToUser(slackUser, buildCommandHelperValue(slackUser.getRealName()), null);
        };

        slackSession.addMessagePostedListener(messagePostedListener);
    }

    private boolean isStartCommand(String message) {

        return startPattern.matcher(message).find();
    }

    private boolean isStopCommand(String message) {

        return message.equalsIgnoreCase("stop pomodoro");
    }

    private void startPomodoro(String message, SlackUser slackUser) {

        Integer delay = getNotificationDelay(message);
        pomodoroNotificationService.startPomodoro(slackUser.getUserName(), delay.longValue(), TimeUnit.SECONDS);
    }

    private void stopPomodoro(SlackUser slackUser) {



        pomodoroNotificationService.stopPomodoro(slackUser);
    }

    private Integer getNotificationDelay(String s) {

        return Integer.valueOf(s.split("\\s")[2]);
    }

    private String buildCommandHelperValue(String username) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Hello ");
        stringBuilder.append(username);
        stringBuilder.append("\n");
        stringBuilder.append("Here are the available commands \n");
        stringBuilder.append("• `start pomodoro <time_in_minutes>` starts a new pomodoro (between 1 and 999 minutes)} \n");
        stringBuilder.append("• `stop pomodoro` stops the pomodoro \n");
        stringBuilder.append("• `help` displays this message");

        return stringBuilder.toString();
    }

    private boolean isNotInstantMessaging(SlackMessagePosted event) {

        return !event.getChannel().getType().equals(SlackChannel.SlackChannelType.INSTANT_MESSAGING);
    }
}