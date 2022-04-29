package ru.kuranov.service;

import org.springframework.stereotype.Service;
import ru.kuranov.security.AccountRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface RoleService {

    Optional<AccountRole> findByName(String name);

    List<AccountRole> findAll();

    Set<AccountRole> findAllByName(String[] roles);
}
