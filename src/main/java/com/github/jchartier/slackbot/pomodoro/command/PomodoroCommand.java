package com.github.jchartier.slackbot.pomodoro.command;

import com.ullink.slack.simpleslackapi.SlackUser;

public interface PomodoroCommand {

    void execute(SlackUser slackUser, String message);
}
