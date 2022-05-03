package ru.gb.gbexternalapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbexternalapi.repository.AccountUserRepository;
import ru.gb.gbexternalapi.security.AccountUser;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountUserRepository userRepository;

    @Override
    public void save(AccountUser user) {
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
