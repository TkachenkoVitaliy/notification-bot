package ru.vtkachenko.notificationbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vtkachenko.notificationbot.service.TelegramBot;

@Slf4j
@Component
public class BotInitializer {
    private final TelegramBot bot;

    @Autowired
    public BotInitializer(TelegramBot bot) {
        this.bot = bot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
            log.info("Bot initialized");
        } catch (TelegramApiException e) {
            log.error("Bot cannot initialize {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
