package ru.itis.workproject.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProfileForm {
    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 3)
    private String login;

}
