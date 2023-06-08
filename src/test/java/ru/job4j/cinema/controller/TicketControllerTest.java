package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmServiceImpl;
import ru.job4j.cinema.service.FilmSessionServiceImpl;
import ru.job4j.cinema.service.HallServiceImpl;
import ru.job4j.cinema.service.TicketServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {
    @Test
    public void whenSaveTicketSuccess() {
        Ticket ticket = new Ticket(1, 2, 3, 4, 5);
        Model model = mock(Model.class);
        User user = new User(1, "Tom", "@tom", "12345");
        TicketServiceImpl ticketService = mock(TicketServiceImpl.class);
        FilmServiceImpl filmService = mock(FilmServiceImpl.class);
        FilmSessionServiceImpl filmSessionService = mock(FilmSessionServiceImpl.class);
        HallServiceImpl hallService = mock(HallServiceImpl.class);
        TicketController controller = new TicketController(ticketService, filmSessionService, filmService, hallService);
        HttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));
        String page = controller.saveTicket(ticket, session, 1, model);
        assertThat(page).isEqualTo("ticket/success");
    }

    @Test
    public void whenSaveTicketFail() {
        Ticket ticket = new Ticket(1, 2, 3, 4, 5);
        Model model = mock(Model.class);
        User user = new User(1, "Tom", "@tom", "12345");
        TicketServiceImpl ticketService = mock(TicketServiceImpl.class);
        FilmServiceImpl filmService = mock(FilmServiceImpl.class);
        FilmSessionServiceImpl filmSessionService = mock(FilmSessionServiceImpl.class);
        HallServiceImpl hallService = mock(HallServiceImpl.class);
        TicketController controller = new TicketController(ticketService, filmSessionService, filmService, hallService);
        HttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        when(ticketService.save(ticket)).thenReturn(Optional.empty());
        String page = controller.saveTicket(ticket, session, 1, model);
        assertThat(page).isEqualTo("errors/failTicket");
    }

}