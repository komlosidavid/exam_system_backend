
package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import lombok.Data;
import java.util.List;
import java.util.UUID;

/**
 * Dto class for {@link User} entity.
 */
@Data
public class UserDto {

    /**
     * ID of the {@link User} entity.
     */
    private UUID id;

    /**
     * Username of the {@link User} entity.
     */
    private String username;

    /**
     * Email of the {@link User} entity.
     */
    private String email;

    /**
     * Full name of the {@link User} entity.
     */
    private String fullName;

    /**
     * {@link List} of {@link TestDto}s of the {@link User} entity.
     */
    private List<TestDto> tests;

    /**
     * {@link Role} of the {@link User} entity.
     */
    private Role role;
}
