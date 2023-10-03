
package inf.unideb.hu.exam.system.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;
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
    private List<UserDto> collaborators;
    /**
     * Set for holding students.
     */
    private List<UserDto> students;
    /**
     * Count of students who are finished.
     */
    private int finishedStudents;
    /**
     * Set for holding question for test.
     */
    private List<QuestionDto> questions;
    /**
     * Creation date.
     */
    private Instant creationDate;
}
