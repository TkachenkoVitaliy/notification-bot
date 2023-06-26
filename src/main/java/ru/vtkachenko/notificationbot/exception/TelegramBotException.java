package ru.vtkachenko.notificationbot.exception;

public class TelegramBotException extends RuntimeException {
    public TelegramBotException() {
        super();
    }

    public TelegramBotException(String message) {
        super(message);
    }

    public TelegramBotException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramBotException(Throwable cause) {
        super(cause);
    }

    protected TelegramBotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
