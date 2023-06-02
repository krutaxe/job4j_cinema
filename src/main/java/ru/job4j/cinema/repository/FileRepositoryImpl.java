package ru.job4j.cinema.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private static final String FIND_BY_ID_FILE = """
            SELECT * FROM files WHERE id = :id
            """;

    private final Sql2o sql2o;

    @Override
    public Optional<File> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(FIND_BY_ID_FILE);
            File file = query.addParameter("id", id)
                    .executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }
}
