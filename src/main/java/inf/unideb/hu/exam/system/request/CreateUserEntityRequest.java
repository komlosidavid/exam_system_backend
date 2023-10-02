
package inf.unideb.hu.exam.system.request;

import inf.unideb.hu.exam.system.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for creating a {@link inf.unideb.hu.exam.system.models.User} entity.
 */
@AllArgsConstructor
@Setter
@Getter
public class CreateUserEntityRequest {
    /**
     * First name of the user.
     */
    private String firstname;
    /**
     * Last name of the user.
     */
    private String lastname;
    private String username;
    private String email;
    private String password;
    private Role role;
}
