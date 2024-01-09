package ru.anastasia.spring.RestApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class NewsDTO {

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 255, message = "Название должно быть от 2 до 255 символов")
    private String title;

    @NotEmpty
    private String content;

    private LocalDateTime publicationDate;


    private UsersDTO userIdFK;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UsersDTO getUserIdFK() {
        return userIdFK;
    }

    public void setUserIdFK(UsersDTO userIdFK) {
        this.userIdFK = userIdFK;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

}
