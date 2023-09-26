
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.UUID)
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
    private User creator;
    /**
     * Collaborators for the test.
     */
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private Set<User> collaborators;
    /**
     * Set for holding students.
     */
    @OneToMany(
            fetch = FetchType.LAZY
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
            cascade = CascadeType.ALL,
            mappedBy = "test"
    )
    @Builder.Default
    private final Set<Question> questions = new HashSet<>();
    /**
     * Creation date.
     */
    @Builder.Default
    private final Instant creationDate = new Date().toInstant();
}
