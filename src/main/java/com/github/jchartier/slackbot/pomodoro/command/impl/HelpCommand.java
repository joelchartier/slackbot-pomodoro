package com.github.jchartier.slackbot.pomodoro.command.impl;

import com.github.jchartier.slackbot.pomodoro.command.PomodoroCommand;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;

public class HelpCommand implements PomodoroCommand {

    // TODO: Centralized this character
    private static final String DOT = "•";

    private SlackSession slackSession;

    public HelpCommand(SlackSession slackSession) {

        this.slackSession = slackSession;
    }

    @Override
    public void execute(SlackUser slackUser, String message) {

        slackSession.sendMessageToUser(slackUser, buildCommandHelperValue(slackUser.getRealName()), null);
    }

    private String buildCommandHelperValue(String username) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Hello ");
        stringBuilder.append(username);
        stringBuilder.append("\n");
        stringBuilder.append("Here are the available commands \n");
        stringBuilder.append(DOT);
        stringBuilder.append(" `start pomodoro <time_in_minutes>` starts a new pomodoro (between 1 and 999 minutes)} \n");
        stringBuilder.append(DOT);
        stringBuilder.append(" `stop pomodoro` stops the pomodoro \n");
        stringBuilder.append(DOT);
        stringBuilder.append(" `list` list all active pomodoros \n");
        stringBuilder.append(DOT);
        stringBuilder.append(" `help` displays this message");

        return stringBuilder.toString();
    }
}
