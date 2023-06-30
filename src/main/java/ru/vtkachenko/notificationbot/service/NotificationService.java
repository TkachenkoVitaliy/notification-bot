package ru.vtkachenko.notificationbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vtkachenko.notificationbot.exception.TelegramBotException;
import ru.vtkachenko.notificationbot.model.SendMessageRequest;
import ru.vtkachenko.notificationbot.model.SendMessageResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NotificationService {

    private final NotificationTelegramBot bot;

    @Autowired
    public NotificationService(NotificationTelegramBot bot) {
        this.bot = bot;
    }

    public void onUpdateReceived(Update update) {
        String KICKED = "kicked";

        if (update.hasMyChatMember()) {
            String status = update.getMyChatMember().getNewChatMember().getStatus();
            if (status.equals(KICKED)) {
                log.info("User with chatId {} stop and block bot", update.getMyChatMember().getChat().getId());
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            log.info("Bot get message - '{}' from user - {}", messageText, chatId);

            if ("/start".equals(messageText)) {
                sendMessage(chatId, String.format("Ваш ID - %s", chatId));
            } else {
                sendMessage(chatId, "Unknown command");
            }
        }
    }

    public SendMessageResponse sendMessageToUsers(SendMessageRequest messageRequest) {
        List<Long> chatIds = messageRequest.getChatIds();
        String text = messageRequest.getMessageText();

        List<Long> successfulIds = new ArrayList<>();
        List<Long> abortedIds = new ArrayList<>();

        chatIds.forEach(id -> {
            try {
                sendMessage(id, text);
                successfulIds.add(id);
            } catch (TelegramBotException e) {
                abortedIds.add(id);
            }
        });

        SendMessageResponse result =  new SendMessageResponse(successfulIds, abortedIds);

        if (abortedIds.isEmpty()) {
            log.info("Result send message '{}' - {}", text, result);
        } else {
            if(successfulIds.isEmpty()) {
                log.error("Result send message '{}' - {}", text, result);
            } else {
                log.warn("Result send message '{}' - {}", text, result);
            }
        }


        return result;

    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.enableNotification();
        message.setChatId(chatId);
        message.setText(text);
        try {
            bot.execute(message);
            log.debug("Sent message - '{}' to user with chatID - {}", text, chatId);
        } catch (TelegramApiException e) {
            log.error("Error occurred when try to send message - '{}' to user with chatId - {}.", text, chatId, e);
            throw new TelegramBotException(e);
        }
    }
}
