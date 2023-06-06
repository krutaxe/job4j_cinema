package ru.job4j.cinema.repository;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOG_USER_DB = LoggerFactory.getLogger(
            UserRepositoryImpl.class.getName()
    );
    private static final String ADD_SQL = """
            INSERT INTO users (name, email, password) 
            VALUES (:name, :email, :password)
            """;

    private static final String FIND_ALL_SQL = """
            SELECT * FROM users ORDER BY id
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM users WHERE :id
            """;

    private static final String FIND_BY_EMAIL_PWD = """
            SELECT * FROM users WHERE email = :email AND password = :password
            """;

    private final Sql2o sql2o;

    public Optional<User> findUserByEmailAndPwd(String email, String pwd) {
        try (Connection cn = sql2o.open()) {
             Query query = cn.createQuery(FIND_BY_EMAIL_PWD);
             User user = query.addParameter("email", email)
                     .addParameter("password", pwd)
                     .executeAndFetchFirst(User.class);
             return Optional.ofNullable(user);
        }
    }

    public List<User> findAll() {
        try (Connection cn = sql2o.open()) {
            Query query = cn.createQuery(FIND_ALL_SQL);
            return query.executeAndFetch(User.class);
        }
    }

    public Optional<User> add(User user) {
        try (Connection cn = sql2o.open()) {
            Query query = cn.createQuery(ADD_SQL, true)
                    .addParameter("name", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());

            try {
                int generatedId = query.executeUpdate().getKey(Integer.class);
                user.setId(generatedId);
                return Optional.of(user);
            } catch (Exception e) {
                LOG_USER_DB.error("Error add user", e);
            }
            return Optional.empty();
        }
    }

    public Optional<User> findById(int id) {
        try (Connection cn = sql2o.open()) {
            Query query = cn.createQuery(FIND_BY_ID_SQL);
            User user = query.addParameter("id", id)
                    .executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}
