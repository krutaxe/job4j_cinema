package ru.job4j.cinema.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FilmSessionRepositoryImpl implements FilmSessionRepository {
    private static final String FIND_ALL_SQL = """
            SELECT * FROM film_sessions ORDER BY id
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM film_sessions WHERE id = :id
            """;

    private final Sql2o sql2o;

    @Override
    public Optional<FilmSession> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(FIND_BY_ID);
            FilmSession session = query.setColumnMappings(FilmSession.COLUMN_MAPPING)
                    .addParameter("id", id).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(session);
        }
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(FIND_ALL_SQL);
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING)
                    .executeAndFetch(FilmSession.class);
        }
    }
}
