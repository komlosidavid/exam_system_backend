
package inf.unideb.hu.exam.system.request;

import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for creating a {@link User} entity.
 */
@AllArgsConstructor
@Setter
@Getter
public class CreateUserEntityRequest {

    /**
     * Firstname property of {@link User} entity.
     */
    private String firstname;

    /**
     * Lastname property of {@link User} entity.
     */
    private String lastname;

    /**
     * Username property of {@link User} entity.
     */
    private String username;

    /**
     * Email property of {@link User} entity.
     */
    private String email;

    /**
     * Password property of {@link User} entity.
     */
    private String password;

    /**
     * {@link Role} property of {@link User} entity.
     */
    private Role role;
}
