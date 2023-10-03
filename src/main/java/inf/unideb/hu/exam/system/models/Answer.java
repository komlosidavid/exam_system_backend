
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import inf.unideb.hu.exam.system.models.enums.AnswerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity class for holding answer data.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Answer {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;
    private String answer;
    private boolean isCorrect;
    @Enumerated(EnumType.STRING)
    private AnswerType type;
}
