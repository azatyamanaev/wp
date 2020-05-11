package ru.itis.workproject.services;


import ru.itis.workproject.models.User;

import java.util.Optional;

public interface UsersService {
    Optional<User> find(Long id);
    Optional<User> findUserByLogin(String login);
    void updateUser(User user);
}
