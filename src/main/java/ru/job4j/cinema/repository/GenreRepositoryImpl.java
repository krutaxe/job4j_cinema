package ru.job4j.cinema.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Genre;

import java.util.Optional;
@Repository
@AllArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

    private static final String FIND_BY_ID_GENRE = """
            SELECT * FROM genres WHERE id = :id
            """;

    private final Sql2o sql2o;

    @Override
    public Optional<Genre> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(FIND_BY_ID_GENRE);
            Genre genre = query.addParameter("id", id)
                    .executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        }
    }
}
