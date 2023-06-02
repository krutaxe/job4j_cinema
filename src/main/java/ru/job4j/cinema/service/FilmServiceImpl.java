package ru.job4j.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FilmRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepositoryImpl filmRepository;
    private final GenreService genreService;

    public List<FilmDto> findAll() {
        List<FilmDto> filmDtoList = new ArrayList<>();
        List<Film> films = (List<Film>) filmRepository.findAll();
        for (Film film : films) {
            FilmDto filmDto = new FilmDto();
            filmDto.setId(film.getId());
            filmDto.setName(film.getName());
            filmDto.setDescription(film.getDescription());
            filmDto.setYear(film.getYear());
            filmDto.setMinimalAge(film.getMinimalAge());
            filmDto.setDurationInMinutes(film.getDurationInMinutes());
            Optional<Genre> genre = genreService.findById(film.getGenreId());
            if (genre.isPresent()) {
                filmDto.setGenre(genre.get().getName());
            } else {
                filmDto.setGenre("Неизвестно");
            }
            filmDto.setFileId(film.getFileId());

            filmDtoList.add(filmDto);
        }
        return filmDtoList;
    }

    public Optional<FilmDto> findById(int id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isEmpty()) {
            throw new RuntimeException();
        }
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.get().getId());
        filmDto.setName(film.get().getName());
        filmDto.setDescription(film.get().getDescription());
        filmDto.setYear(film.get().getYear());
        filmDto.setMinimalAge(film.get().getMinimalAge());
        filmDto.setDurationInMinutes(film.get().getDurationInMinutes());
        Optional<Genre> genre = genreService.findById(film.get().getGenreId());
        if (genre.isPresent()) {
            filmDto.setGenre(genre.get().getName());
        } else {
            filmDto.setGenre("Неизвестно");
        }
        filmDto.setFileId(film.get().getFileId());
        return Optional.of(filmDto);
    }
}
