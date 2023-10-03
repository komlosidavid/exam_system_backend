
package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.enums.Role;
import lombok.Data;

import java.util.*;

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
    private List<TestDto> tests;
    private Role role;
}
