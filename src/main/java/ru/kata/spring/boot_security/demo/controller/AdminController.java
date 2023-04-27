package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepo;

    public AdminController(UserService userService, RoleRepository roleRepo) {
        this.userService = userService;
        this.roleRepo = roleRepo;
    }

    @GetMapping()
    public String viewUsers(Model model, Principal principal) {
        model.addAttribute("usersList", userService.getAllUsers());
        model.addAttribute("rolesAtt", roleRepo.findAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("admin", userService.findByUsername(principal.getName()));
        return "admin";
    }

    @PatchMapping("/{id}")
    public String newEdit(Model model,@ModelAttribute("user") User user, @PathVariable("id") long id) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("rolesAtt", roleRepo.findAll());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String newDelete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/create")
    public String newNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
