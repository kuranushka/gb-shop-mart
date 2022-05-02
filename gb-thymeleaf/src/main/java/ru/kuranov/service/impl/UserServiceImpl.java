package ru.kuranov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kuranov.repository.AccountUserRepository;
import ru.kuranov.repository.AuthorityRepository;
import ru.kuranov.security.AccountUser;
import ru.kuranov.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final AccountUserRepository accountUserRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountUser user = accountUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("NO SUCH USER"));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountUser> findAll() {
        return accountUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountUser> findById(Long id) {
        return accountUserRepository.findById(id);
    }

    @Override
    public void save(AccountUser user) {
        accountUserRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        accountUserRepository.deleteById(id);
    }
}
