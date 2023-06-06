package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByEmailAndPwd(String email, String pwd);
    List<User> findAll();
    Optional<User> add(User user);
    Optional<User> findById(int id);
}
