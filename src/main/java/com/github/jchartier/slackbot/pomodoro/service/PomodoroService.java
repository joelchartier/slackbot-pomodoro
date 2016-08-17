package com.github.jchartier.slackbot.pomodoro.service;

import com.github.jchartier.slackbot.pomodoro.model.Pomodoro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class PomodoroService {

    @Autowired
    private StringRedisTemplate template;

    public void create(String username, long delay, TimeUnit timeUnit) {

        template.opsForValue().set(username, "", delay, timeUnit);
    }

    public List<Pomodoro> list() {

        List<String> userNames = new ArrayList<>(getRedisOperations().keys("*"));

        return userNames.stream()
                .map(userName -> new Pomodoro(userName, getDelay(userName)))
                .collect(Collectors.toList());
    }

    public void delete(String username) {

        template.delete(username);
    }

    public boolean pomodoroExists(String username) {

        return template.opsForValue().get(username) != null;
    }

    private long getDelay(String username) {

        return getRedisOperations().getExpire(username, TimeUnit.MINUTES);
    }

    private RedisOperations<String, String> getRedisOperations() {

        return template.opsForValue().getOperations();
    }
}
