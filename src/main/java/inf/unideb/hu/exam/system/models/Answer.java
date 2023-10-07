
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import inf.unideb.hu.exam.system.models.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
     * ID.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Reference for {@link Question} entity.
     */
    @ManyToOne
    @JsonBackReference
    private Question question;

    /**
     * Answer property.
     */
    private String answer;

    /**
     * Correctness of the answer.
     */
    private boolean correct;

    /**
     * {@link AnswerType} of the answer.
     */
    @Enumerated(EnumType.STRING)
    private AnswerType type;
}
