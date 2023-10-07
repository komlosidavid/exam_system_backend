
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.time.Instant;
import java.util.UUID;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
     * OpensAt property.
     */
    private Date opensAt;

    /**
     * Creator of the test.
     */
    @ManyToOne
    @JsonBackReference
    private User creator;

    /**
     * Collaborators for the test.
     */
    @OneToMany
    @JsonManagedReference
    @Builder.Default
    private List<User> collaborators = new ArrayList<>();

    /**
     * Set for holding students.
     */
    @OneToMany
    @JsonManagedReference
    @Builder.Default
    private List<User> students = new ArrayList<>();

    /**
     * Count of students who are finished.
     */
    @Builder.Default
    private int finishedStudents = 0;

    /**
     * Set for holding question for test.
     */
    @OneToMany
    @JsonManagedReference
    @Builder.Default
    private List<Question> questions = new ArrayList<>();

    /**
     * Creation date.
     */
    @Builder.Default
    private final Instant creationDate = new Date().toInstant();
}
