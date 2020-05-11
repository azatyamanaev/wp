package ru.itis.workproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.workproject.dto.LogInDto;
import ru.itis.workproject.dto.TokenDto;
import ru.itis.workproject.services.AuthService;

@RestController
public class LogInRestController {

    @Autowired
    private AuthService authService;

    @PostMapping("/api/logIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody LogInDto data) {
        return ResponseEntity.ok(authService.logIn(data));
    }

}
