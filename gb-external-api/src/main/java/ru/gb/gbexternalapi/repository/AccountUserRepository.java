package ru.gb.gbexternalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.gbexternalapi.security.AccountUser;

import java.util.Optional;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {
    Optional<AccountUser> findByUsername(String username);
}
