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
        FilmSession filmSession = filmSessionService.findById(id).get();
        FilmDto film = filmService.findById(filmSession.getFilmId()).get();
        Hall hall = hallService.findById(filmSession.getHallsId()).get();
        model.addAttribute("ses", filmSession);
        model.addAttribute("film", film);
        model.addAttribute("hall", hall);
        List<Integer> rows = new ArrayList<>();
        for (int i = 1; i <= hall.getRowCount(); i++) {
            rows.add(i);
        }
        List<Integer> places = new ArrayList<>();
        for (int i = 1; i <= hall.getPlaceCount(); i++) {
            places.add(i);
        }
        model.addAttribute("rows", rows);
        model.addAttribute("places", places);
        return "formByTicket";
    }

    @PostMapping("/saveTicket/{id}")
    public String saveTicket(@ModelAttribute Ticket ticket,
                             HttpSession session, @PathVariable("id") int id, Model model) {
        ticket.setUserId(Session.getUserId(session));
        ticket.setSessionId(id);
        Optional<Ticket> optionalTicket = ticketService.save(ticket);
        if (optionalTicket.isEmpty()) {
            return "redirect:/failByTicket";
        }
        return "redirect:/successByTicket/" + ticket.getId();
    }

    @GetMapping("/successByTicket/{id}")
    public String successByTicket(Model model, @PathVariable("id") int id) {
        Ticket ticket = ticketService.findById(id).get();
        FilmSession filmSession = filmSessionService.findById(ticket.getSessionId()).get();
        FilmDto filmDto = filmService.findById(filmSession.getFilmId()).get();
        Hall hall = hallService.findById(filmSession.getHallsId()).get();
        model.addAttribute("hall", hall);
        model.addAttribute("film", filmDto);
        model.addAttribute("sess", filmSession);
        model.addAttribute("ticket", ticket);
        return "successByTicket";
    }

    @GetMapping("/failByTicket")
    public String fail() {
        return "failByTicket";
    }
}
