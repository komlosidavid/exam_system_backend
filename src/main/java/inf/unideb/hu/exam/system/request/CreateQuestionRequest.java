package inf.unideb.hu.exam.system.request;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
public class CreateQuestionRequest {
    private String question;
    private float points;
    private String type;
    private List<CreateAnswerRequest> answers;
}
