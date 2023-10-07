
package inf.unideb.hu.exam.system.request;

import inf.unideb.hu.exam.system.models.Test;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Request payload for creating a {@link Test} entity.
 */
@Setter
@Getter
public class CreateTestEntityRequest {

    /**
     * Subject of the test.
     */
    private String subject;

    /**
     * Creator of the test.
     */
    private UUID creator;

    /**
     * OpensAt date of {@link Test} entity.
     */
    private Date opensAt;

    /**
     * Collaborators for the test.
     */
    private List<UUID> collaborators;

    /**
     * Students for the test.
     */
    private List<UUID> students;

    /**
     * Set for holding question for test.
     */
    private List<CreateQuestionRequest> questions;
}
