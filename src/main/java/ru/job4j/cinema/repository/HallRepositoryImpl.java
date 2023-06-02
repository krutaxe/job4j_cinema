package ru.job4j.cinema.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HallRepositoryImpl implements HallRepository {
    private static final String FIND_BY_ID = """
            SELECT * FROM halls WHERE id = :id
            """;

    private final Sql2o sql2o;

    @Override
    public Optional<Hall> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(FIND_BY_ID);
            Hall hall = query.setColumnMappings(Hall.COLUMN_MAPPING).addParameter("id", id)
                    .executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }
}
