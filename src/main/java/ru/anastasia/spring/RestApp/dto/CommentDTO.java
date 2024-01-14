package ru.anastasia.spring.RestApp.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class CommentDTO {

    @NotEmpty
    private String text;

    private LocalDateTime date;

    private UsersDTO usersIdFK;

    private NewsDTO newsIdFk;

    private Long commentId;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UsersDTO getUsersIdFK() {
        return usersIdFK;
    }

    public void setUsersIdFK(UsersDTO usersIdFK) {
        this.usersIdFK = usersIdFK;
    }

    public NewsDTO getNewsIdFk() {
        return newsIdFk;
    }

    public void setNewsIdFk(NewsDTO newsIdFk) {
        this.newsIdFk = newsIdFk;
    }
}
