package ru.anastasia.spring.RestApp.exception.comment;

import java.time.LocalDateTime;

public class CommentErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public CommentErrorResponse(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
