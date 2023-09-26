package inf.unideb.hu.exam.system.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAnswerRequest {

    private String answer;
    private boolean isCorrect;
    private String type;
}
