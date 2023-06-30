package ru.vtkachenko.notificationbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.vtkachenko.notificationbot.config.BotConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service
public class NotificationTelegramBot extends SpringWebhookBot {
    private final BotConfig config;

    public NotificationTelegramBot(BotConfig config) {
        super(config.getSetWebhookInstance(), config.getToken());
        this.config = config;
        this.init();
    }

    private void init() {
        try {
            URL url = new URL(String.format("https://api.telegram.org/bot%s/setWebhook?url=%s", config.getToken(), config.getWebHookPath()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int statusCode = connection.getResponseCode();
            log.info("Register Webhook with status code - {}", statusCode);
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotPath() {
        return config.getWebHookPath();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    // Не используемый метод, не совсем понятно зачем создатель библиотеки сделал его обязательным для имплементации
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

}
