package com.github.jchartier.slackbot.pomodoro.configuration;

import com.github.jchartier.slackbot.pomodoro.notifier.CompletedPomodoroNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RedisListenerConfiguration {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("__key*__:*"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(CompletedPomodoroNotifier receiver) {
        return new MessageListenerAdapter(receiver, "notifyUser");
    }
}