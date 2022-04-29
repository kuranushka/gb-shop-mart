package ru.kuranov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.security.AccountRole;

@Repository
public interface AuthorityRepository extends JpaRepository<AccountRole, Long> {
}
