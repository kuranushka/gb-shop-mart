package ru.kuranov.service;

import org.springframework.stereotype.Service;
import ru.kuranov.security.AccountUser;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<AccountUser> findAll();

    Optional<AccountUser> findById(Long id);

    void save(AccountUser user);

    void deleteById(Long id);
}
