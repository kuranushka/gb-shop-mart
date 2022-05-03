package ru.gb.gbexternalapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapi.security.UserDto;
import ru.gb.gbexternalapi.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    List<UserDto> getuUserList() {
        return userService.findAll();
    }

    @GetMapping({"/{userId}"})
    ResponseEntity<?> getUser(@PathVariable("userId") Long id) {
        if (id != null) {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        } else {
            throw new RuntimeException(String.format("user with id %d not exist", id));
        }
    }

    @PostMapping
    ResponseEntity<?> handlePost(@Validated @RequestBody UserDto userDto) {
        UserDto registerUser = userService.register(userDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v1/user" + registerUser.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping({"/{userId}"})
    ResponseEntity<?> handleUpdate(@PathVariable("userId") Long id,
                                   @Validated @RequestBody UserDto userDto) {
        if (id != null) {
            userService.update(userDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new RuntimeException(String.format("user with id %d not exist", id));
        }
    }

    @DeleteMapping({"/{userId}"})
    void deleteById(@PathVariable("userId") Long id) {
        userService.deleteById(id);
    }
}
