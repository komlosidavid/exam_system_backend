
package inf.unideb.hu.exam.system.request;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Request payload for creating a {@link inf.unideb.hu.exam.system.models.Test} entity.
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
     * Collaborators for the test.
     */
    private List<UUID> collaborators;
    /**
     * Set for holding question for test.
     */
    private Set<UUID> questions;
}