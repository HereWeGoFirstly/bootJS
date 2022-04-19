package ru.herewego.bootJS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users/")
public class UserController {

    @GetMapping("/user")
    public String user() {
        return "user";
    }
}