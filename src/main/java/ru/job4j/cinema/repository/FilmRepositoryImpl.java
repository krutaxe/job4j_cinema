package ru.job4j.cinema.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FilmRepositoryImpl implements FilmsRepository {
    private static final String FIND_ALL_SQL = """
            SELECT * FROM films ORDER BY id
            """;

    private static final String FIND_BY_ID_FILM = """
            SELECT * FROM films WHERE id = :id
            """;

    private final Sql2o sql2o;

    @Override
    public Collection<Film> findAll() {
        try (Connection cn = sql2o.open()) {
            Query query = cn.createQuery(FIND_ALL_SQL);
            return query.setColumnMappings(Film.COLUMN_MAPPING)
                    .executeAndFetch(Film.class);
        }
    }

    @Override
    public Optional<Film> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(FIND_BY_ID_FILM);
            Film film = query.setColumnMappings(Film.COLUMN_MAPPING).addParameter("id", id)
                    .executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }
}
