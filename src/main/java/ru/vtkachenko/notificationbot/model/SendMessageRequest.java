package ru.vtkachenko.notificationbot.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SendMessageRequest {
    private List<Long> chatIds = new ArrayList<>();
    private String messageText;
}
