package com.github.jchartier.slackbot.pomodoro.command.impl;

import com.github.jchartier.slackbot.pomodoro.command.PomodoroCommand;
import com.github.jchartier.slackbot.pomodoro.model.Pomodoro;
import com.github.jchartier.slackbot.pomodoro.service.PomodoroService;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListCommand implements PomodoroCommand {

    private SlackSession slackSession;
    private PomodoroService pomodoroService;
    private MessageSource messageSource;

    public ListCommand(SlackSession slackSession, PomodoroService pomodoroService, MessageSource messageSource) {

        this.slackSession = slackSession;
        this.pomodoroService = pomodoroService;
        this.messageSource = messageSource;
    }

    @Override
    public void execute(SlackUser slackUser, String message) {

        List<Pomodoro> activePomodoros = listActivePomodoros();

        if (activePomodoros.isEmpty()) {

            slackSession.sendMessageToUser(slackUser,
                    messageSource.getMessage("pomodoro.none.active", null, Locale.ENGLISH),
                    null);
        } else {

            slackSession.sendMessageToUser(slackUser, getMessageToSend(activePomodoros), null);
        }
    }

    private String getMessageToSend(List<Pomodoro> activePomodoros) {

        return activePomodoros.stream()
                        .map(pomodoro -> messageSource.getMessage("command.list", new Object[] {pomodoro.getUsername()}, Locale.ENGLISH))
                        .reduce("", (result, name) -> (String.format("%s %s\n", result, name)));
    }

    private List<Pomodoro> listActivePomodoros() {

        List<Pomodoro> pomodoros = pomodoroService.list();
        List<SlackUser> slackUsers = (List<SlackUser>) slackSession.getUsers();

        return pomodoros.stream()
                .filter(activePomodoro -> isValidUsername(slackUsers, activePomodoro))
                .collect(Collectors.toList());
    }

    private boolean isValidUsername(List<SlackUser> activeUsers,
                                    Pomodoro pomodoro) {

        return activeUsers.stream()
                .filter(activeUser -> activeUser.getUserName().equals(pomodoro.getUsername()))
                .findFirst()
                .isPresent();
    }
}
