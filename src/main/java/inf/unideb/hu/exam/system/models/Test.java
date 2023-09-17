
package inf.unideb.hu.exam.system.models;

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
    private String subject;
    /**
     * Creator of the test.
     */
    @ManyToOne
    private User creator;
    /**
     * Collaborators for the test.
     */
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Set<User> collaborators;
    /**
     * Set for holding students.
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    private Set<User> students;
    /**
     * Count of students who are finished.
     */
    @Builder.Default
    private int finishedStudents = 0;
    /**
     * Set for holding question for test.
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @Builder.Default
    private final Set<Question> questions = new HashSet<>();
    /**
     * Creation date.
     */
    @Builder.Default
    private final Instant creationDate = new Date().toInstant();
}
