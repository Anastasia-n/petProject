package ru.anastasia.spring.RestApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UsersDTO {

    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 2, max = 45, message = "Логин должен быть от 2 до 45 символов")
    private String login;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 5, max = 255)
    private String password;

    @NotEmpty
    @Email
    private String email;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
