package ru.kuranov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kuranov.security.AccountRole;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<AccountRole, Long> {
    Optional<AccountRole> findByName(String name);

    @Query(value = "select * from account_role where name in :names", nativeQuery = true)
    Set<AccountRole> findByNames(@Param("names") String[] names);

}
