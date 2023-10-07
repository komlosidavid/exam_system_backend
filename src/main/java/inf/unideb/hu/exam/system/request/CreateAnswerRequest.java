
package inf.unideb.hu.exam.system.request;

import inf.unideb.hu.exam.system.models.Answer;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for create a new {@link Answer} entity.
 */
@Setter
@Getter
public class CreateAnswerRequest {

    /**
     * Answer of the {@link Answer} entity.
     */
    private String answer;

    /**
     * Correctness of the {@link Answer} entity
     */
    private boolean correct;

    /**
     * Type of the {@link Answer} entity.
     */
    private String type;
}
