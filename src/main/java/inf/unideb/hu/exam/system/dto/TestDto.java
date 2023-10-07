
package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.Test;
import lombok.Data;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Dto class for {@link Test} entity.
 */
@Data
public class TestDto {
    /**
     * ID of the {@link Test} entity.
     */
    private UUID id;

    /**
     * Subject of the {@link Test} entity.
     */
    private String subject;

    /**
     * OpensAt date of the {@link Test} entity.
     */
    private Date opensAt;

    /**
     * Creator of the {@link Test} entity.
     */
    private UserDto creator;

    /**
     * Collaborators of the {@link Test} entity.
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
