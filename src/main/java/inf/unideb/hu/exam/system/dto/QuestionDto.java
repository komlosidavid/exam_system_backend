package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.enums.QuestionType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuestionDto {

    private UUID id;
    private String question;
    private List<AnswerDto> answers;
    private QuestionType type;
    private float points;
}
