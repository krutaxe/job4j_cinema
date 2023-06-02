package ru.job4j.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class IndexController {

    @GetMapping("/index")
    public String index(Model model, HttpSession session, HttpServletRequest request) {
        Session.getSessionUser(model, session);
        return "index";
    }
}
