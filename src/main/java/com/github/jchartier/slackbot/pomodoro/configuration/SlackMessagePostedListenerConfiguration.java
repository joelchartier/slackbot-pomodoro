package com.github.jchartier.slackbot.pomodoro.configuration;

import com.github.jchartier.slackbot.pomodoro.command.PomodoroCommandFactory;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SlackMessagePostedListenerConfiguration {

    @Autowired
    private SlackSession slackSession;

    @Autowired
    private PomodoroCommandFactory pomodoroCommandFactory;

    @PostConstruct
    public void register() {

        slackSession.addMessagePostedListener(buildSlackMessagePostedListener());
    }

    private SlackMessagePostedListener buildSlackMessagePostedListener() {

        return (event, session) -> {

                if (isNotInstantMessaging(event)) {

                    return;
                }

                String message = event.getMessageContent();
                SlackUser slackUser = event.getSender();

                pomodoroCommandFactory.getCommand(message)
                        .execute(slackUser, message);
            };
    }

    private boolean isNotInstantMessaging(SlackMessagePosted event) {

        return !event.getChannel().getType().equals(SlackChannel.SlackChannelType.INSTANT_MESSAGING);
    }
}