
package inf.unideb.hu.exam.system.request;

import inf.unideb.hu.exam.system.models.Question;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Class for creating {@link Question} entities.
 */
@Setter
@Getter
public class CreateQuestionRequest {

    /**
     * Question property of {@link Question} entity.
     */
    private String question;

    /**
     * Points property of {@link Question} entity.
     */
    private float points;

    /**
     * {@link QuestionType} property of {@link Question} entity.
     */
    private String type;

    /**
     * {@link List} of {@link CreateAnswerRequest}s property of {@link Question} entity.
     */
    private List<CreateAnswerRequest> answers;
}
