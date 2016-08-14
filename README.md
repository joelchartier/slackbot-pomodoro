# Pomodoro Slackbot
This is a SlackBot allowing users to use the Pomodoro Technique.

## Currently available commands
- `start pomodoro <time_in_minutes>` starts a pomodoro
- `stop pomodoro` stops a pomodoro
- `help` displays help

## Configuration
You will need to update the `application.properties` file wich can be found in the `resources` directory:
```
# the host name of your running redis instance
spring.redis.host=127.0.0.1

# the port of your running redis instance
spring.redis.port=6379

# the slack bot token that can be generated through the slack app diretory (http://slack.com/apps/)
slack.bot.token=xoxb-12345678910-abcdefghijklmnopqrstuvwx
```

## External Dependencies
* Spring boot
* Spring data
* [Ullink/simple-slack-api](https://github.com/Ullink/simple-slack-api)
