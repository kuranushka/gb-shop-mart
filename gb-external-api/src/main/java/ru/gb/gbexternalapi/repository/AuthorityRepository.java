package ru.gb.gbexternalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.gbexternalapi.security.AccountRole;


@Repository
public interface AuthorityRepository extends JpaRepository<AccountRole, Long> {
}
