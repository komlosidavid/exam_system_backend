
package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.Question;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Dto class for {@link inf.unideb.hu.exam.system.models.Test} entity.
 */
@Data
public class TestDto {
    /**
     * Primary key.
     */
    private UUID id;
    /**
     * Subject of the test.
     */
    private String subject;
    /**
     * Creator of the test.
     */
    private UserDto creator;
    /**
     * Collaborators for the test.
     */
    private Set<UserDto> collaborators;
    /**
     * Set for holding question for test.
     */
    private Set<Question> questions;
    /**
     * Creation date.
     */
    private Instant creationDate;
}
