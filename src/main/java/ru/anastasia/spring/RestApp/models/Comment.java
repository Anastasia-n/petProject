package ru.anastasia.spring.RestApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @NotEmpty
    @Column(name = "text")
    private String text;

    @JoinColumn(name = "user_idfk")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Users usersIdFK;

   // @NotEmpty
    @JoinColumn(name = "news_idfk")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private News newsIdFk;

    public Comment() {
    }

    public Comment(LocalDateTime date, String text, Users usersIdFK, News newsIdFk) {
        this.date = date;
        this.text = text;
        this.usersIdFK = usersIdFK;
        this.newsIdFk = newsIdFk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Users getUsersIdFK() {
        return usersIdFK;
    }

    public void setUsersIdFK(Users usersIdFK) {
        this.usersIdFK = usersIdFK;
    }

    public News getNewsIdFk() {
        return newsIdFk;
    }

    public void setNewsIdFk(News newsIdFk) {
        this.newsIdFk = newsIdFk;
    }
}
