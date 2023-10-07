
package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.Answer;
import inf.unideb.hu.exam.system.models.enums.AnswerType;
import lombok.Data;
import java.util.UUID;

/**
 * Dto class of {@link Answer}.
 */
@Data
class AnswerDto {

    /**
     * ID of the {@link Answer} entity.
     */
    private UUID id;

    /**
     * Answer of the {@link Answer} entity.
     */
    private String answer;

    /**
     * Correctness of the {@link Answer} entity.
     */
    private boolean correct;

    /**
     * {@link AnswerType} of the {@link Answer} entity.
     */
    private AnswerType type;
}
