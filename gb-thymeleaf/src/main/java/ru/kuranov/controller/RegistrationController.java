package ru.kuranov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kuranov.model.dto.AccountUserRegistrationDto;
import ru.kuranov.security.AccountRole;
import ru.kuranov.security.AccountUser;
import ru.kuranov.service.AccountService;
import ru.kuranov.service.RoleService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final AccountService accountService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/app/products/registration")
    public String registration(Model model) {
        AccountUserRegistrationDto userRegistrationDto = AccountUserRegistrationDto.builder().build();
        model.addAttribute("userRegistrationDto", userRegistrationDto);
        return "user-registration";
    }


    @PostMapping("/app/products/registration")
    public String registration(@Valid AccountUserRegistrationDto userRegistrationDto) {

        Optional<AccountRole> role = roleService.findByName("USER");
        if (role.isPresent()) {
            AccountUser accountUser = AccountUser.builder()
                    .username(userRegistrationDto.getUsername())
                    .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                    .build();
            accountUser.setRoles(Collections.singleton(role.get()));
            accountService.save(accountUser);

        }
        return "redirect:/login";
    }

    @ExceptionHandler({BindException.class})
    public String handleException(Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Set<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toSet());
            model.addAttribute("errors", errors);
        }
        AccountUserRegistrationDto userRegistrationDto = AccountUserRegistrationDto.builder().build();
        model.addAttribute("userRegistrationDto", userRegistrationDto);
        return "user-registration";
    }
}
