package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilmSession {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "halls_id", "hallsId",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;
    private int filmId;
    private int hallsId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;
}
