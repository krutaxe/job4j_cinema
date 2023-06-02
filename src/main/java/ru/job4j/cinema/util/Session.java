package ru.job4j.cinema.util;

import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import javax.servlet.http.HttpSession;

public final class Session {

    private Session() {
    }

    public static void getSessionUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            user = new User();
            user.setName("Гость");
        }

        model.addAttribute("userSession", user);
    }

    public static int getUserId(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user.getId();
    }
}
