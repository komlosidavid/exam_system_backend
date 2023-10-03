package inf.unideb.hu.exam.system.dto;

import inf.unideb.hu.exam.system.models.enums.AnswerType;
import lombok.Data;

import java.util.UUID;

@Data
public class AnswerDto {

    private UUID id;
    private String answer;
    private boolean isCorrect;
    private AnswerType type;
}
