
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class for holding question data's.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Question {

    /**
     * UUID based primary key.
     */
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String question;
    @ManyToOne
    private Test test;
    /**
     * Set for holding answers.
     */
    @OneToMany(
            mappedBy = "question"
    )
    @Builder.Default
    private Set<Answer> answers = new HashSet<>();
    /**
     * Type of the question.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    /**
     * Points of the question.
     */
    @Column(nullable = false)
    private float points;

}
