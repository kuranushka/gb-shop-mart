package ru.kuranov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kuranov.repository.AccountUserRepository;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AccountUserRepository accountUserRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
