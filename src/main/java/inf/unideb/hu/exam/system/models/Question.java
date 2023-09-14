
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    @GeneratedValue
    private UUID id;
    /**
     * Creator of the question.
     */
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private User creator;
    /**
     * Set of the collaborators.
     * This property can be null.
     */
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private Set<User> collaborators;
    /**
     * Set for holding answers.
     */
    @OneToMany(
            fetch = FetchType.LAZY
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
    @Builder.Default
    private float points = 0f;
}
