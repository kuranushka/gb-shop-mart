package ru.gb.gbexternalapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbapi.security.UserDto;
import ru.gb.gbexternalapi.mapper.UserMapper;
import ru.gb.gbexternalapi.repository.AccountUserRepository;
import ru.gb.gbexternalapi.repository.AuthorityRepository;
import ru.gb.gbexternalapi.repository.RoleRepository;
import ru.gb.gbexternalapi.security.AccountRole;
import ru.gb.gbexternalapi.security.AccountStatus;
import ru.gb.gbexternalapi.security.AccountUser;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final AccountUserRepository accountUserRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountUser user = accountUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("NO SUCH USER"));
        return user;
    }


    @Override
    public UserDto register(UserDto userDto) {
        if (accountUserRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException(String.format("user with %s username is already exist", userDto.getUsername()));
        }
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        AccountRole accountRole = roleRepository.findByName("ROLE_USER").get();
        accountUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        accountUser.setRoles(Set.of(accountRole));
        accountUser.setStatus(AccountStatus.ACTIVE);

        AccountUser registeredUser = accountUserRepository.save(accountUser);
        log.info("User with {} username is registered", registeredUser.getUsername());
        return userMapper.toUserDto(registeredUser);
    }

    @Override
    public UserDto update(UserDto userDto) {
        return userMapper.toUserDto(accountUserRepository.save(userMapper.toAccountUser(userDto)));
    }

    @Override
    public List<UserDto> findAll() {
        log.info("findAll users was called");
        return accountUserRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountUser> findByUsername(String username) {
        return accountUserRepository.findByUsername(username);
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.toUserDto(accountUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user with %d iD not found", id))));
    }

    @Override
    public void deleteById(Long id) {
        accountUserRepository.deleteById(id);
    }
}
