package ru.vtkachenko.notificationbot.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SendMessageResponse {
    private List<Long> successfulIds = new ArrayList<>();
    private List<Long> abortedIds = new ArrayList<>();

    private String errorMessage;

    public SendMessageResponse(List<Long> successfulIds, List<Long> abortedIds) {
        this.successfulIds = successfulIds;
        this.abortedIds = abortedIds;
        this.errorMessage = abortedIds.isEmpty() ? null : String.format("Не удалось отправить сообщение пользователям с id - %s", abortedIds);
    }

    @Override
    public String toString() {
        return String.format("SendMessageResponse - { successfulIds=%s, abortedIds=%s }. " +
                "Successful count - %s, Aborted Count - %s. %s",
                successfulIds, abortedIds, successfulIds.size(), abortedIds.size(),
                errorMessage != null ? errorMessage : "");
    }
}
