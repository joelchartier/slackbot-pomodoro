# This is a SlackBot allowing users to use the Pomodoro Technique

## Configuration (via application.properties)
* A running redis instance
    * You will find specific redis.config + Dockerfile in the conf/ folder
    * Specific redis configuration are required to receive events notification
* A SlackBot with a valid token

## Currently available commands
- Start a pomodoro
- Stop a pomodoro
- Help