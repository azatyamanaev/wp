package ru.itis.workproject.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.workproject.dto.LogInDto;
import ru.itis.workproject.dto.SignUpDto;
import ru.itis.workproject.dto.TokenDto;
import ru.itis.workproject.models.Role;
import ru.itis.workproject.models.State;
import ru.itis.workproject.models.User;
import ru.itis.workproject.repositories.UsersRepository;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    @Qualifier(value = "usersRepositoryJpaImpl")
    private UsersRepository usersRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public TokenDto logIn(LogInDto form) {
        Optional<User> userOptional = usersRepository.findOneByLogin(form.getLogin());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
                String token = Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("login", user.getLogin())
                        .claim("role", user.getRole().name())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not found");

    }

    @Override
    public boolean signUp(SignUpDto form) {
        Optional<User> clientCandidate;
        User user;
        clientCandidate = usersRepository.findOneByLogin(form.getLogin());
        if (clientCandidate.isEmpty() && !form.getLogin().equals("") && !form.getPassword().equals("") && !form.getEmail().equals("")) {
            user = User.builder()
                    .login(form.getLogin()).email(form.getEmail())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .state(State.NOT_CONFIRMED)
                    .role(Role.USER)
                    .build();
            usersRepository.save(user);
            emailService.sendNotificationAboutRegistration(form);
            return true;
        }
        return false;
    }
}
