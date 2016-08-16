package com.github.jchartier.slackbot.pomodoro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class PomodoroService {

    @Autowired
    private StringRedisTemplate template;

    public void create(String username, long delay, TimeUnit timeUnit) {

        template.opsForValue().set(username, "", delay, timeUnit);
    }

    public List<String> list() {

        Set<String> keys = template.opsForValue().getOperations().keys("*");

        return new ArrayList<>(keys);
    }

    public void delete(String username) {

        template.delete(username);
    }
}
