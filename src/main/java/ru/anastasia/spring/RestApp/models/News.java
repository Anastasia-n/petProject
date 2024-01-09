package ru.anastasia.spring.RestApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
public class News {

    @Id
    @Column(name = "news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 255, message = "Название должно быть от 2 до 255 символов")
    @Column(name = "title")
    private String title;

    @Column(name = "publication_date")
    @NotNull(message = "Дата публикации не должна быть пустой")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime publicationDate;

    @NotEmpty
    @Column(name = "content")
    private String content;

    @JoinColumn(name = "user_idfk")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userIdFK;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsIdFk")
    private List<Comment> commentList;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public News() {
    }

    public News(String title, LocalDateTime publicationDate, String content, Users userIdFK) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.content = content;
        this.userIdFK = userIdFK;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Users getUserIdFK() {
        return userIdFK;
    }

    public void setUserIdFK(Users userIdFK) {
        this.userIdFK = userIdFK;
    }
}
