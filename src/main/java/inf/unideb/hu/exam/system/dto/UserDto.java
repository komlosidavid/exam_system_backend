
package inf.unideb.hu.exam.system.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * Dto class for {@link inf.unideb.hu.exam.system.models.User} entity.
 */
@Data
public class UserDto {
    /**
     * Primary key.
     */
    private UUID id;
    private String username;
    private String email;
    /**
     * Full name of the user.
     */
    private String fullname;
    private Set<TestDto> ownTests;
    private Set<TestDto> collaboratingTests;
}
