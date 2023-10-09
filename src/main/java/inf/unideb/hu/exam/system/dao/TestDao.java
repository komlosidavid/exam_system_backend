
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.Test;
import inf.unideb.hu.exam.system.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * DAO interface for implementing database manipulation
 * methods and function using Spring Data JPA.
 */
@Repository
public interface TestDao extends JpaRepository<Test, UUID> {

    /**
     * Function to get all the {@link Test} entities according to the request.
     * @param creator a {@link User} entity of a {@link Test}.
     * @param collaborator a {@link User} entity of a {@link Test}
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} object of the {@link Test} results.
     */
    Page<Test> findByCreatorOrCollaborators(User creator,
                                            User collaborator,
                                            Pageable pageable);

    /**
     * Function to get a {@link Test} by creator.
     * @param creator a {@link User} entity.
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} object of the {@link Test} results.
     */
    Page<Test> findByCreator(User creator, Pageable pageable);

    /**
     * Function to get {@link Test} entities by collaborators.
     * @param user is the collaborator.
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} object of the {@link Test} results.
     */
    Page<Test> findByCollaborators(User user, Pageable pageable);

    /**
     * Function to get all {@link Test} entities by subject property.
     * @param subject of the {@link Test}.
     * @param username for getting the {@link Test} entities which has access by the {@link User}.
     * @return a {@link List} of {@link Test} entities.
     */
    @Query("""
        SELECT t
            FROM Test t
            inner join User creator on t.creator.id = creator.id
            left join User collaborator on t.id = collaborator.id
            left join User student on t.id = student.id
            WHERE lower(t.subject) LIKE %:subject% AND
            (creator.username = :username or
            collaborator.username = :username or
            student.username = :username)
    """)
    List<Test> findAllBySubjectContaining(@Param("subject") String subject,
                                          @Param("username") String username);
}
