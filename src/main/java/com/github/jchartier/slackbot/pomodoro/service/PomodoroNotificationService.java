package com.github.jchartier.slackbot.pomodoro.service;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PomodoroNotificationService {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private SlackSession slackSession;

    public void startPomodoro(String username, long delay, TimeUnit timeUnit) {

        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(username, "", delay, timeUnit);

        slackSession.sendMessageToUser(username, getStartPomodoroMessage(username, delay, timeUnit), null);
    }

    private String getStartPomodoroMessage(String username, long delay, TimeUnit timeUnit) {

        return String.format("%s, " +
                "your pomodoro of %s %s has been started.\n" +
                "You will be notified when it's completed.\n" +
                "You can also stop your pomodoro by using the command 'stop pomodoro'", username, delay, timeUnit.toString());
    }

    public void stopPomodoro(SlackUser slackUser) {

        template.delete(slackUser.getUserName());
        slackSession.sendMessageToUser(slackUser, "You pomodoro has been stopped", null);
    }
}