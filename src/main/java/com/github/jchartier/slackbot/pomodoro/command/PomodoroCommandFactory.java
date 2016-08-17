package com.github.jchartier.slackbot.pomodoro.command;

import com.github.jchartier.slackbot.pomodoro.command.impl.HelpCommand;
import com.github.jchartier.slackbot.pomodoro.command.impl.ListCommand;
import com.github.jchartier.slackbot.pomodoro.command.impl.StartCommand;
import com.github.jchartier.slackbot.pomodoro.command.impl.StopCommand;
import com.github.jchartier.slackbot.pomodoro.service.PomodoroNotificationService;
import com.github.jchartier.slackbot.pomodoro.service.PomodoroService;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PomodoroCommandFactory {

    private static final Pattern startPattern = Pattern.compile("start pomodoro [0-9]{1,3}", Pattern.CASE_INSENSITIVE);

    @Autowired
    private SlackSession slackSession;

    @Autowired
    private PomodoroService pomodoroService;

    @Autowired
    private PomodoroNotificationService pomodoroNotificationService;

    public PomodoroCommand getCommand(String message) {

        if (isStartCommand(message)) {

            return new StartCommand(pomodoroNotificationService);
        }

        if (isStopCommand(message)) {

            return new StopCommand(pomodoroNotificationService);
        }

        if (isListCommand(message)) {

            return new ListCommand(slackSession, pomodoroService);
        }

        return new HelpCommand(slackSession);
    }

    private boolean isStartCommand(String message) {

        return startPattern.matcher(message).find();
    }

    private boolean isStopCommand(String message) {

        return message.equalsIgnoreCase("stop pomodoro");
    }

    private boolean isListCommand(String message) {

        return message.equalsIgnoreCase("list");
    }
}
