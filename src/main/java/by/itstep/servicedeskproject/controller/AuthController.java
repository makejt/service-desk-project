package by.itstep.servicedeskproject.controller;

import by.itstep.servicedeskproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    private UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String homeForm() {
        return "index";
    }

}