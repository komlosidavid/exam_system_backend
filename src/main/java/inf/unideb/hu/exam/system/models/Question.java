
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String question;
    @ManyToOne(fetch = FetchType.LAZY)
    private Test test;
    /**
     * Set for holding answers.
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
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
