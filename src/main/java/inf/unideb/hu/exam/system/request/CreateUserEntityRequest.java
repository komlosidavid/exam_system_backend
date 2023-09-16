
package inf.unideb.hu.exam.system.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Request payload for creating a {@link inf.unideb.hu.exam.system.models.User} entity.
 */
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
}
