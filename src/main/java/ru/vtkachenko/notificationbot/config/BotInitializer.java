package ru.vtkachenko.notificationbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vtkachenko.notificationbot.service.TelegramWHBot;

@Slf4j
@Component
public class BotInitializer {

    private final TelegramWHBot bot;
    private final BotConfig botConfig;

    public BotInitializer(TelegramWHBot bot, BotConfig botConfig) {
        this.bot = bot;
        this.botConfig = botConfig;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot, SetWebhook.builder().url(botConfig.getPath()).build());
    }


//    private final TelegramBot bot;
//
//    @Autowired
//    public BotInitializer(TelegramBot bot) {
//        this.bot = bot;
//    }
//
//    @EventListener({ContextRefreshedEvent.class})
//    public void init() throws TelegramApiException {
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        try {
//            telegramBotsApi.registerBot(bot);
//            log.info("Bot initialized");
//        } catch (TelegramApiException e) {
//            log.error("Bot cannot initialize {}", e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
//    }
}
