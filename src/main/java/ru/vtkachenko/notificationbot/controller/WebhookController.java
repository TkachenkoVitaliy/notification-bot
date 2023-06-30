package ru.vtkachenko.notificationbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtkachenko.notificationbot.service.NotificationService;

@Slf4j
@RestController
public class WebhookController {
    private final NotificationService notificationService;

    public WebhookController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/")
    public void onUpdateReceived(@RequestBody Update update) {
        log.debug("POST MAPPING - {}", update.toString());
        notificationService.onUpdateReceived(update);
    }
}
