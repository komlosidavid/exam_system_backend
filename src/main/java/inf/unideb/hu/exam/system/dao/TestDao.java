
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.Test;
import inf.unideb.hu.exam.system.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO interface for implementing database manipulation
 * methods and function using Spring Data JPA.
 */
@Repository
public interface TestDao extends JpaRepository<Test, UUID> {

    Page<Test> findByCreatorOrCollaborators(User creator,
                                            User collaborator,
                                            Pageable pageable);

    Page<Test> findByCreator(User creator, Pageable pageable);
    Page<Test> findByCollaborators(User user, Pageable pageable);
}
