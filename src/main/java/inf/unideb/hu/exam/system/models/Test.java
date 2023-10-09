
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @ManyToMany(mappedBy = "collaboratorTests")
    @JsonManagedReference
    @Builder.Default
    private List<User> collaborators = new ArrayList<>();

    /**
     * Set for holding students.
     */
    @ManyToMany(mappedBy = "studentTests")
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
