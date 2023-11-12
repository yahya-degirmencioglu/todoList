package de.yahya.todolist.controller;

import de.yahya.todolist.model.User;
import de.yahya.todolist.repository.UserRepository;
import de.yahya.todolist.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sess")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @GetMapping("login")
    public String loginForm(Model model) {
        if (loginService.isLoggedIn())
            return "redirect:/tasks";
        return "login-form";
    }

    @PostMapping("login")
    public String login(String email, String password, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            loginService.doLogIn(user);
            return "redirect:/tasks";
        }
        model.addAttribute("error", true);
        return "login-form";
    }
    @GetMapping("logout")
    public String logout(Model model) {
        loginService.doLogOut();
        model.addAttribute("data", "Abgemeldet");
        return "redirect:/sess/login";
    }
}

