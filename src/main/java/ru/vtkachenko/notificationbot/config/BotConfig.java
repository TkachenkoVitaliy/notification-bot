package ru.vtkachenko.notificationbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {

    @Value("${bot.username}")
    String botName;

    @Value("${bot.token}")
    String token;

    @Value("${bot.path}")
    String path;
}
