package ru.job4j.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmServiceImpl;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class FilmController {
    private final FilmServiceImpl filmService;

    @GetMapping("/films")
    public String findAll(Model model, HttpSession session) {
        Session.getSessionUser(model, session);
        model.addAttribute("films", filmService.findAll());
        return "films/films";
    }

    @GetMapping("/film/{id}")
    public String showFilm(Model model, HttpSession session, @PathVariable("id") int id) {
        Session.getSessionUser(model, session);
        Optional<FilmDto> film = filmService.findById(id);
        if (film.isPresent()) {
            model.addAttribute("film", film.get());
        } else {
            throw new RuntimeException();
        }
        return "films/showFilm";
    }

}
