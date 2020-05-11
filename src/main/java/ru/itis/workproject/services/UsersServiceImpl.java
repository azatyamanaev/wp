package ru.itis.workproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.workproject.models.User;
import ru.itis.workproject.repositories.UsersRepository;


import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {
    @Autowired
    @Qualifier(value = "usersRepositoryJdbcTemplateImpl")
    private UsersRepository usersRepository;
    @Override
    public Optional<User> find(Long id) {
        return usersRepository.find(id);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return usersRepository.findOneByLogin(login);
    }

    @Override
    public void updateUser(User user) {
        usersRepository.update(user);
    }
}
