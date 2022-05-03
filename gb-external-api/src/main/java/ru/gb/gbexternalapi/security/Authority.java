package ru.gb.gbexternalapi.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    static final long serialVersionUID = -5_702_839_107_845_469_980L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permission")
    private String permission;

    @ManyToMany(mappedBy = "authorities", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<AccountRole> roles;

    @Override
    public String getAuthority() {
        return this.permission;
    }
}
