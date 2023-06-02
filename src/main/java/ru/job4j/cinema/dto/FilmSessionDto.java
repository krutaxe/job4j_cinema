package ru.job4j.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilmSessionDto {
    private int id;
    private String film;
    private String hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String price;

}
