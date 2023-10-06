
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

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
    @Column(nullable = false)
    private String question;
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
    private QuestionType type;
    /**
     * Points of the question.
     */
    @Column(nullable = false)
    private float points;
}
