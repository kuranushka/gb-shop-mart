package ru.gb.gbexternalapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbapi.security.AuthenticationUserDto;
import ru.gb.gbexternalapi.security.AccountRole;
import ru.gb.gbexternalapi.security.AccountUser;
import ru.gb.gbexternalapi.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationUserDto authenticationUserDto) {
        final String username = authenticationUserDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                authenticationUserDto.getPassword()
        ));
        Optional<AccountUser> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {

            String jwtToken = jwtTokenProvider.createToken(username, userOptional.get().getRoles()
                    .stream()
                    .map(AccountRole::getName)
                    .collect(Collectors.toList()));

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", jwtToken);
            return ResponseEntity.ok(response);
        } else {
            throw new BadCredentialsException("user with this credentials not found");
        }
    }
}
