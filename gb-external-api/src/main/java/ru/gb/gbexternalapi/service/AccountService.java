package ru.gb.gbexternalapi.service;

import org.springframework.stereotype.Service;
import ru.gb.gbexternalapi.security.AccountUser;


@Service
public interface AccountService {

    void save(AccountUser user);

    boolean findByUsername(String username);
}
