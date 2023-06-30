package ru.vtkachenko.notificationbot.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@Getter
@Setter
@ToString
public class BotConfig {

    private String botName;

    private String token;

    private String webHookPath;

    private SetWebhook setWebhookInstance;

    @Autowired
    public BotConfig(@Value("${bot.username}") String botName,
                     @Value("${bot.token}") String token,
                     @Value("${bot.webHookPath}") String webHookPath) {
        this.botName = botName;
        this.token = token;
        this.webHookPath = webHookPath;
        this.setWebhookInstance = SetWebhook.builder().url(webHookPath).build();
    }
}
