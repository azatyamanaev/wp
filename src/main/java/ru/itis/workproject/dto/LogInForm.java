package ru.itis.workproject.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LogInForm {
    @NotEmpty
    private String login;
    @NotEmpty
    @Size(min = 3)
    private String password;
}
