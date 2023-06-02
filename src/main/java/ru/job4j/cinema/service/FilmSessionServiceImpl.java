package ru.job4j.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.FilmSessionRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmSessionServiceImpl implements FilmSessionService {
    private final FilmSessionRepositoryImpl filmSessionRepository;
    private final FilmServiceImpl filmService;

    private final HallServiceImpl hallService;

    @Override
    public Optional<FilmSession> findById(int id) {
        return filmSessionRepository.findById(id);
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        List<FilmSessionDto> sessionDtoList = new ArrayList<>();
        List<FilmSession>  sessionList = (List<FilmSession>) filmSessionRepository.findAll();
        for (FilmSession session : sessionList) {
            FilmSessionDto filmSessionDto = new FilmSessionDto();
            filmSessionDto.setId(session.getId());

            Optional<FilmDto> film = filmService.findById(session.getFilmId());
            if (film.isPresent()) {
                filmSessionDto.setFilm(film.get().getName());
            } else {
                filmSessionDto.setFilm("Фильм не найден");
            }

            Optional<Hall> hall = hallService.findById(session.getHallsId());
            if (hall.isPresent()) {
                filmSessionDto.setHall(hall.get().getName());
            } else {
                filmSessionDto.setHall("Зал не найден");
            }

            filmSessionDto.setStartTime(session.getStartTime());
            filmSessionDto.setEndTime(session.getEndTime());
            filmSessionDto.setPrice(session.getPrice() + "р.");
            sessionDtoList.add(filmSessionDto);
        }
        return sessionDtoList;
    }
}
