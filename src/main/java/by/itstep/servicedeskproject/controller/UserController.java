package by.itstep.servicedeskproject.controller;

import by.itstep.servicedeskproject.model.Role;
import by.itstep.servicedeskproject.model.User;
import by.itstep.servicedeskproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
@Controller
@SessionAttributes(value = "user")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        if (user.getDepartment().equals("Управление цифрового развития") ||
                user.getDepartment().equals("Отдел поддержки внутренних пользователей")) {
            user.setRoles(Collections.singleton(new Role(2, "ROLE_MANAGER")));
        } else {
            user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));}

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/users";
    }
    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "update_user";
    }
    @PostMapping("/user/save/{id}")
    public String saveUser(@PathVariable("id") Integer id, @ModelAttribute("user") User user) {
        User updUser = userService.update(id, user);
        userService.saveUser(updUser);
        return "redirect:/users";
    }
    @GetMapping("/user/delete/{id}")
        public String deleteUser(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}