package com.github.jchartier.slackbot.pomodoro.command.impl;

import com.github.jchartier.slackbot.pomodoro.command.PomodoroCommand;
import com.github.jchartier.slackbot.pomodoro.model.Pomodoro;
import com.github.jchartier.slackbot.pomodoro.service.PomodoroService;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ListCommand implements PomodoroCommand {

    // TODO: Centralized this character
    private static final String DOT = "•";

    private SlackSession slackSession;
    private PomodoroService pomodoroService;

    public ListCommand(SlackSession slackSession, PomodoroService pomodoroService) {

        this.slackSession = slackSession;
        this.pomodoroService = pomodoroService;
    }

    @Override
    public void execute(SlackUser slackUser, String message) {

        List<Pomodoro> activePomodoros = listActivePomodoros();

        if (activePomodoros.isEmpty()) {

            slackSession.sendMessageToUser(slackUser, ">>> No active pomodoro found", null);
        } else {

            slackSession.sendMessageToUser(slackUser, getMessageToSend(activePomodoros), null);
        }
    }

    private String getMessageToSend(List<Pomodoro> activePomodoros) {

        return activePomodoros.stream()
                        .map(pomodoro -> (pomodoro.getUsername() + ", with " + pomodoro.getCurrentDelay() + " minutes remaining."))
                        .reduce("", (result, name) -> (String.format("%s%s %s\n", result, DOT, name)));
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
