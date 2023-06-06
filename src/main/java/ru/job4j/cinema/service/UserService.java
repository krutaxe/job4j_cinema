package ru.job4j.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepositoryImpl userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String pwd) {
        return userRepository.findUserByEmailAndPwd(email, pwd);
    }
}
