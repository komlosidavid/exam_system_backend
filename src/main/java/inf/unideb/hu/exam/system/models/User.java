
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import inf.unideb.hu.exam.system.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

/**
 * Entity class for holding user infos.
 */
@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements UserDetails {

    /**
     * UUID based primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * First name of the user.
     */
    @JsonIgnore
    @Column(nullable = false)
    private String firstname;
    /**
     * Last name of the user.
     */
    @JsonIgnore
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true, updatable = false, length = 50)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String password;
    @OneToMany
    private List<Token> tokens;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "creator"
    )
    private Set<Test> ownTests;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "collaborators"
    )
    private Set<Test> collaboratingTests;
    @Builder.Default
    private Instant registeredAt = new Date().toInstant();

    /**
     * Function to get the full name of the user.
     * @return the user's full name.
     */
    @JsonProperty
    public String getFullname() {
        return lastname + " " + firstname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
