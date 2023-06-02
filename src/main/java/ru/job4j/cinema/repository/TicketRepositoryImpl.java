package ru.job4j.cinema.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private static final String ADD_SQL = """
            INSERT INTO tickets (session_id, row_number, place_number, user_id) 
            VALUES (:session, :row, :place, :user)
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM tickets WHERE id = :id
            """;

    private final Sql2o sql2o;

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(ADD_SQL, true)
                    .addParameter("session", ticket.getSessionId())
                    .addParameter("row", ticket.getRowNumber())
                    .addParameter("place", ticket.getPlaceNumber())
                    .addParameter("user", ticket.getUserId());
            try {
                int generatedId = query.executeUpdate().getKey(Integer.class);
                ticket.setId(generatedId);
                return Optional.of(ticket);
            } catch (Exception exception) {
                return Optional.empty();
            }

        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(FIND_BY_ID);
            Ticket ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .addParameter("id", id).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }
}
