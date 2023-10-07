
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 * Entity class for holding question data's.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Question {

    /**
     * UUID based primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Question property.
     */
    @Column(nullable = false)
    private String question;

    /**
     * {@link Test} property.
     */
    @ManyToOne
    @JsonBackReference
    private Test test;

    /**
     * Set for holding answers.
     */
    @OneToMany
    @JsonManagedReference
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    /**
     * Type of the question.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)

    /**
     * {@link QuestionType} property.
     */
    private QuestionType type;

    /**
     * Points of the question.
     */
    @Column(nullable = false)
    private float points;
}
