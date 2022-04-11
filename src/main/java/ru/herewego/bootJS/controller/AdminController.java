package ru.herewego.bootJS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.herewego.bootJS.model.User;
import ru.herewego.bootJS.service.RoleService;
import ru.herewego.bootJS.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/api/users/")
public class AdminController {

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/access-error")
    public String accessError() {
        return "access-error";
    }

    @GetMapping("/admin")
    public String list(Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.listUsers());
        return "users/list";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        User currentUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", new User());
        return "users/new";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/api/users/admin";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("newuser") User user
            , @RequestParam(value = "roles1") Integer role) {
        user.addRole(roleService.getRole(role == 1 ? "ROLE_ADMIN" : "ROLE_USER"));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.add(user);
        return "redirect:/api/users/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("editedUser", new User());
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.setOfRoles());
        return "users/edit";
    }

    @PostMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user
            , @PathVariable("id") int id
            , @RequestParam(value = "roles1") int role
            , @RequestParam(value = "nameToEdit") String nameToEdit
            , @RequestParam(value = "surnameToEdit") String surnameToEdit
            , @RequestParam(value = "passwordToEdit") String passwordToEdit) {
        user.addRole(roleService.getRole(role == 1 ? "ROLE_ADMIN" : "ROLE_USER"));
        user.setName(nameToEdit);
        user.setSurname(surnameToEdit);
        user.setPassword(new BCryptPasswordEncoder().encode(passwordToEdit));
        userService.update(id, user);
        return "redirect:/api/users/admin";
    }
}


