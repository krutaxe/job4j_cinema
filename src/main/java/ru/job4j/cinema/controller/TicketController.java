package ru.job4j.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmServiceImpl;
import ru.job4j.cinema.service.FilmSessionServiceImpl;
import ru.job4j.cinema.service.HallServiceImpl;
import ru.job4j.cinema.service.TicketServiceImpl;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class TicketController {
    private final TicketServiceImpl ticketService;
    private final FilmSessionServiceImpl filmSessionService;
    private final FilmServiceImpl filmService;
    private final HallServiceImpl hallService;

    @GetMapping("/formTicket/{id}")
    public String byTicket(Model model, @PathVariable("id") int id, HttpSession session) {
        Session.getSessionUser(model, session);
        Optional<FilmSession> filmSession = filmSessionService.findById(id);
        if (filmSession.isEmpty()) {
            model.addAttribute("message", "Сеанс не найден");
            return "errors/404";
        }
        Optional<FilmDto> filmDto = filmService.findById(filmSession.get().getFilmId());
        if (filmDto.isEmpty()) {
            model.addAttribute("message", "Фильм не найден");
            return "errors/404";
        }
        Optional<Hall> hall = hallService.findById(filmSession.get().getHallsId());
        if (hall.isEmpty()) {
            model.addAttribute("message", "Кинозал не найден");
            return "errors/404";
        }
        model.addAttribute("ses", filmSession.get());
        model.addAttribute("film", filmDto.get());
        model.addAttribute("hall", hall.get());
        List<Integer> rows = new ArrayList<>();
        for (int i = 1; i <= hall.get().getRowCount(); i++) {
            rows.add(i);
        }
        List<Integer> places = new ArrayList<>();
        for (int i = 1; i <= hall.get().getPlaceCount(); i++) {
            places.add(i);
        }
        model.addAttribute("rows", rows);
        model.addAttribute("places", places);
        return "ticket/form";
    }

    @PostMapping("/saveTicket/{id}")
    public String saveTicket(@ModelAttribute Ticket ticket,
                             HttpSession session, @PathVariable("id") int id, Model model) {
        ticket.setUserId(Session.getUserId(session));
        ticket.setSessionId(id);
        Optional<Ticket> optionalTicket = ticketService.save(ticket);
        if (optionalTicket.isEmpty()) {
            return "errors/failTicket";
        }
        model.addAttribute("ticket", optionalTicket.get());
        return "ticket/success";
    }
}
