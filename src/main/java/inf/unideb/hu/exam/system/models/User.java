
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    /**
     * FullName property.
     */
    private String fullName;

    /**
     * Username property.
     */
    @Column(nullable = false, unique = true, updatable = false, length = 50)
    private String username;

    /**
     * Email property.
     */
    @Column(nullable = false)
    private String email;

    /**
     * {@link Role} property.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Password property.
     */
    @Column(nullable = false)
    private String password;

    /**
     * {@link List} of {@link Token} property.
     */
    @OneToMany
    @JsonManagedReference
    private List<Token> tokens;

    /**
     * {@link List} of {@link Test} property.
     */
    @OneToMany
    @JsonManagedReference
    @Builder.Default
    private List<Test> tests = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_collaborator_tests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id")
    )
    @JsonBackReference
    private List<Test> collaboratorTests = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_student_tests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id")
    )
    @JsonBackReference
    private List<Test> studentTests = new ArrayList<>();

    /**
     * RegisteredAt property.
     */
    @Builder.Default
    private Instant registeredAt = new Date().toInstant();

    /**
     * Method to set the fullName property.
     */
    public void createFullName() {
        if (firstname != null && lastname != null) {
            fullName = lastname + " " + firstname;
        }
    }

    /**
     * Function to get a {@link Collection} of {@link GrantedAuthority}s.
     * @return a {@link Collection} of {@link GrantedAuthority}s.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Function to check if the account is expired.
     * @return a boolean of the expiration.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Function to check if the account is locked.
     * @return a boolean of locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Function to check if the credentials expired.
     * @return a boolean of expiration.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Function to check if the account is enabled.
     * @return a boolean of enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
