package ru.vtkachenko.notificationbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtkachenko.notificationbot.config.BotConfig;
import ru.vtkachenko.notificationbot.model.SendMessageRequest;
import ru.vtkachenko.notificationbot.model.SendMessageResponse;

@Service
public class TelegramWHBot extends TelegramWebhookBot {

    private final BotConfig config;

    public TelegramWHBot (BotConfig config) {
        super(config.getToken());
        this.config = config;
    }
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        System.out.println(update);
        return null;
    }

    @Override
    public String getBotPath() {
        return config.getPath();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public SendMessageResponse sendMessageToUsers(SendMessageRequest messageRequest) {
        return null;
    }
}
