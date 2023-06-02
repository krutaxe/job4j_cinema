package ru.job4j.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionServiceImpl;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class FilmSessionController {
    private final FilmSessionServiceImpl sessionService;

    @GetMapping("/sessions")
    public String findAll(Model model, HttpSession session) {
        Session.getSessionUser(model, session);
        List<FilmSessionDto> filmSessions = (List<FilmSessionDto>) sessionService.findAll();
        model.addAttribute("sessions", filmSessions);
        return "filmSessions";
    }
}
