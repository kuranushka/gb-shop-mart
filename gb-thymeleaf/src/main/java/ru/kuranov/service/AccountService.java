package ru.kuranov.service;

import org.springframework.stereotype.Service;
import ru.kuranov.security.AccountUser;

@Service
public interface AccountService {

    void save(AccountUser user);

    boolean findByUsername(String username);
}
