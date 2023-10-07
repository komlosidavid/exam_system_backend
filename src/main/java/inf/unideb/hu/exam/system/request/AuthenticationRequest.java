
package inf.unideb.hu.exam.system.request;

import inf.unideb.hu.exam.system.models.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for authentication.
 */
@Setter
@Getter
public class AuthenticationRequest {

    /**
     * Username of the {@link User} entity.
     */
    private String username;

    /**
     * Password of the {@link User} entity.
     */
    private String password;
}
