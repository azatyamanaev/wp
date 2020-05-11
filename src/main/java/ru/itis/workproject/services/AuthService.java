package ru.itis.workproject.services;

import ru.itis.workproject.dto.LogInDto;
import ru.itis.workproject.dto.SignUpDto;
import ru.itis.workproject.dto.TokenDto;
import ru.itis.workproject.models.User;

public interface AuthService {
    TokenDto logIn(LogInDto form);
    boolean signUp(SignUpDto form);
}
