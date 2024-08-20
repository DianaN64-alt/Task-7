package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model, @AuthenticationPrincipal User user) {

        List<User> all = userService.findAll();
        Optional<User> roleUser = all.stream()
                .filter(roles -> roles.getRoles()
                                         .stream()
                                         .anyMatch(role -> role.name().equals("ROLE_USER")) && roles.getId() == user.getId())
                .findFirst();
        if (roleUser.isPresent()) {
            model.addAttribute("users", roleUser.get());
        } else {
            model.addAttribute("users", all);
        }
        return "user";
    }

    @GetMapping(value = "/{id}")
    public String deleteUserById(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/user";
    }

    @GetMapping(value = "/update/{id}")
    public String updateUser(@PathVariable("id")int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "updateUser";
    }

    @PostMapping(value = "/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable int id) {
        userService.update(user, id);
        return "redirect:/user";
    }
}
