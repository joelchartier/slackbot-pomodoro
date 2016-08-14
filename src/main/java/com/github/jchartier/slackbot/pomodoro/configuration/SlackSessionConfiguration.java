package com.github.jchartier.slackbot.pomodoro.configuration;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class SlackSessionConfiguration {

    @Value("${slack.bot.token}")
    private String slackBotToken;

    private SlackSession slackSession;

    @Bean
    public SlackSession slackSession() throws IOException {
        slackSession = SlackSessionFactory.createWebSocketSlackSession(slackBotToken);
        slackSession.connect();

        return slackSession;
    }

    @PreDestroy
    public void closeSession() {

        try {

            slackSession.disconnect();
        } catch (IOException ignored) {
        }
    }
}