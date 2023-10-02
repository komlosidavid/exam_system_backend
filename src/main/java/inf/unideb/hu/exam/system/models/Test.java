
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class for holding test infos.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Test {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue
    private UUID id;
    /**
     * Subject of the test.
     */
    @Column(nullable = false)
    private String subject;
    /**
     * Creator of the test.
     */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    /**
     * Collaborators for the test.
     */
    @OneToMany
    private Set<User> collaborators;
    /**
     * Set for holding students.
     */
    @OneToMany
    private Set<User> students;
    /**
     * Count of students who are finished.
     */
    @Builder.Default
    private int finishedStudents = 0;
    /**
     * Set for holding question for test.
     */
    @OneToMany
    private Set<Question> questions;
    /**
     * Creation date.
     */
    @Builder.Default
    private final Instant creationDate = new Date().toInstant();
}
