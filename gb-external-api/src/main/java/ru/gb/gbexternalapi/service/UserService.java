package ru.gb.gbexternalapi.service;

import org.springframework.stereotype.Service;
import ru.gb.gbapi.security.UserDto;
import ru.gb.gbexternalapi.security.AccountUser;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    UserDto register(UserDto userDto);

    UserDto update(UserDto userDto);

    List<UserDto> findAll();

    Optional<AccountUser> findByUsername(String username);

    UserDto findById(Long id);

    void deleteById(Long id);
}