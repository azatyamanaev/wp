package ru.itis.workproject.dto;

import lombok.Data;

@Data
public class LogInDto {
    private String login;
    private String password;

    public LogInDto() {
    }

    public LogInDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
