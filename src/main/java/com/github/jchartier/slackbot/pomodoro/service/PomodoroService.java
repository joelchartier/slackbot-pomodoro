package com.github.jchartier.slackbot.pomodoro.service;

import com.github.jchartier.slackbot.pomodoro.dto.Pomodoro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class PomodoroService {

    @Autowired
    private StringRedisTemplate template;

    private ValueOperations<String, String> ops;

    @PostConstruct
    public void init() {

         ops = template.opsForValue();
    }

    public void create(String username, long delay, TimeUnit timeUnit) {

        ops.set(username, "", delay, timeUnit);
    }

    public List<Pomodoro> list() {

        List<String> userNames = new ArrayList<>(ops.getOperations().keys("*"));

        return userNames.stream()
                .map(userName -> new Pomodoro(userName, getDelay(userName)))
                .collect(Collectors.toList());
    }

    public void delete(String username) {

        template.delete(username);
    }

    private long getDelay(String username) {

        return ops.getOperations().getExpire(username, TimeUnit.MINUTES);
    }
}
