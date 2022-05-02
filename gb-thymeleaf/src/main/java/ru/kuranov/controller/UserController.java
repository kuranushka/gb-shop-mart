package ru.kuranov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kuranov.security.AccountRole;
import ru.kuranov.security.AccountUser;
import ru.kuranov.service.RoleService;
import ru.kuranov.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String viewAll(Model model) {
        List<AccountUser> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-view-all";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable(name = "id") Long id) {
        Optional<AccountUser> user = userService.findById(id);
        if (user.isPresent()) {
            List<AccountRole> roles = roleService.findAll();
            Set<String> userRoles = user.get()
                    .getRoles()
                    .stream()
                    .map(AccountRole::getName)
                    .collect(Collectors.toSet());

            model.addAttribute("user", user.get());
            model.addAttribute("roles", roles);
            model.addAttribute("userRoles", userRoles);
            return "user-edit";
        } else {
            return "redirect:/app/users";
        }
    }

    @PostMapping("/edit/{id}")
    public String editUser(AccountUser user,
                           @RequestParam(name = "rolesArray", required = false) String[] rolesArray) {
        Set<AccountRole> roles = roleService.findAllByName(rolesArray);
        Optional<AccountUser> userOptional = userService.findById(user.getId());
        if (userOptional.isPresent()) {
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
        }
        return "redirect:/app/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/app/users";
    }
}
