package com.github.jchartier.slackbot.pomodoro.command.impl;

import com.github.jchartier.slackbot.pomodoro.command.PomodoroCommand;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class HelpCommand implements PomodoroCommand {

    private SlackSession slackSession;
    private MessageSource messageSource;

    public HelpCommand(SlackSession slackSession, MessageSource messageSource) {

        this.slackSession = slackSession;
        this.messageSource = messageSource;
    }

    @Override
    public void execute(SlackUser slackUser, String message) {

        slackSession.sendMessageToUser(slackUser, buildCommandHelperValue(slackUser.getRealName()), null);
    }

    private String buildCommandHelperValue(String username) {


        return messageSource.getMessage("command.help", new Object[]{username}, Locale.ENGLISH) +
                System.lineSeparator() +
                messageSource.getMessage("command.help.start", null, Locale.ENGLISH) +
                System.lineSeparator() +
                messageSource.getMessage("command.help.stop", null, Locale.ENGLISH) +
                System.lineSeparator() +
                messageSource.getMessage("command.help.list", null, Locale.ENGLISH) +
                System.lineSeparator() +
                messageSource.getMessage("command.help.help", null, Locale.ENGLISH);
    }
}
