package ru.gb.gbexternalapi.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "account_user")
public class AccountUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<AccountRole> roles;

    @Builder.Default
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @Builder.Default
    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Builder.Default
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @Builder.Default
    @Column(name = "enabled")
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = this.roles.stream()
                .map(AccountRole::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        authorities.addAll(mapRolesToAuthorities(this.roles));
        return authorities;

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<AccountRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

