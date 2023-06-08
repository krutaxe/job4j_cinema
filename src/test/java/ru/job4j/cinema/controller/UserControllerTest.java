package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.controller.UserController;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class UserControllerTest {

    @Test
    public void whenLoginPageFail() {
        Model model = mock(Model.class);
        UserService service = mock(UserService.class);
        HttpSession session = mock(HttpSession.class);
        UserController controller = new UserController(service);
        String page = controller.loginPage(model, true, session);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("users/login");
    }

    @Test
    public void whenAddUserSuccess() {
        User user = new User(1, "Tom", "@mail", "12345");
        Model model = mock(Model.class);
        UserService service = mock(UserService.class);
        UserController controller = new UserController(service);
        when(service.add(user)).thenReturn(Optional.of(user));
        String page = controller.registration(model, user);
        assertThat(page).isEqualTo("redirect:/loginPage");
    }

    @Test
    public void whenAddUserFail() {
        User user = new User(1, "Tom", "@tom", "12345");
        Model model = mock(Model.class);
        UserService service = mock(UserService.class);
        UserController controller = new UserController(service);
        when(service.add(user)).thenReturn(Optional.empty());
        String page = controller.registration(model, user);
        assertThat(page).isEqualTo("redirect:/formAddUser");
    }

    @Test
    public void whenLogOut() {
        UserService service = mock(UserService.class);
        UserController controller = new UserController(service);
        String page = controller.logout(new MockHttpSession());
        assertThat(page).isEqualTo("redirect:/loginPage");
    }
}