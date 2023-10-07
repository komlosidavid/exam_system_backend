
package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.Answer;
import inf.unideb.hu.exam.system.models.Question;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import lombok.Data;
import java.util.List;
import java.util.UUID;

/**
 * Dto class for {@link Question} entity.
 */
@Data
public class QuestionDto {

    /**
     * ID of the {@link Question} entity.
     */
    private UUID id;

    /**
     * Question of the {@link Question} entity.
     */
    private String question;

    /**
     * {@link List} of {@link AnswerDto} objects of the {@link Question} entity.
     */
    private List<AnswerDto> answers;

    /**
     * {@link QuestionType} of the {@link Question} entity.
     */
    private QuestionType type;

    /**
     * Points of the {@link Question} entity.
     */
    private float points;
}
