package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {
    @Test
    public void whenFindAll() {
        Model model = mock(Model.class);
        List<FilmDto> filmDtoList = new ArrayList<>();
        FilmServiceImpl service = mock(FilmServiceImpl.class);
        FilmController controller = new FilmController(service);
        when(service.findAll()).thenReturn(filmDtoList);
        String page = controller.findAll(model, new MockHttpSession());
        assertThat(page).isEqualTo("films/films");
    }

    @Test
    public void whenFindById() {
        Model model = mock(Model.class);
        FilmDto filmDto = new FilmDto(1, "", "", 2000, 6, 120, "", 3);
        FilmServiceImpl service = mock(FilmServiceImpl.class);
        FilmController controller = new FilmController(service);
        when(service.findById(1)).thenReturn(Optional.of(filmDto));
        String page = controller.showFilm(model, new MockHttpSession(), 1);
        assertThat(page).isEqualTo("films/showFilm");
    }
}