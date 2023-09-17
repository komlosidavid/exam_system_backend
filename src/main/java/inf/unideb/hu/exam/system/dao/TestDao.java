
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO interface for implementing database manipulation
 * methods and function using Spring Data JPA.
 */
@Repository
public interface TestDao extends JpaRepository<Test, UUID> {
    Page<Test> findAll(Pageable pageable);
}
