package ru.vtkachenko.notificationbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vtkachenko.notificationbot.model.SendMessageRequest;
import ru.vtkachenko.notificationbot.model.SendMessageResponse;
import ru.vtkachenko.notificationbot.service.NotificationService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessageToUsers(@RequestBody SendMessageRequest messageRequest) {
        log.info("Get request - {}", messageRequest.toString());

        if (messageRequest.getChatIds().isEmpty()) {
            log.warn("Get request with empty chatIds");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong request - chatIds cannot be empty list");
        }

        SendMessageResponse messageResponse = notificationService.sendMessageToUsers(messageRequest);

        if (messageResponse.getSuccessfulIds().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        } else {
            return ResponseEntity.ok().body(messageResponse);
        }
    }
}
