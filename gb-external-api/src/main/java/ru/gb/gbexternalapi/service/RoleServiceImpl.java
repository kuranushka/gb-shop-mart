package ru.gb.gbexternalapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbexternalapi.repository.RoleRepository;
import ru.gb.gbexternalapi.security.AccountRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountRole> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountRole> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<AccountRole> findAllByName(String[] roles) {
        return roleRepository.findByNames(roles);
    }
}
