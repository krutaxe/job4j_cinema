package ru.job4j.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/formAddUser")
    public String getUser(Model model, HttpSession session) {
        Session.getSessionUser(model, session);
        model.addAttribute("users", userService.findAll());
        return "users/addUser";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/formAddUser";
        }
        return "redirect:/loginPage";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(
            name = "fail", required = false) Boolean fail, HttpSession session) {
        Session.getSessionUser(model, session);
        model.addAttribute("fail", fail != null);
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb =
                userService.findUserByEmailAndPwd(user.getEmail(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
