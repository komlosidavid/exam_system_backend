
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO interface for implementing database manipulation
 * methods and function using Spring Data JPA.
 */
@Repository
public interface UserDao extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(@PathVariable String username);
    Optional<User> findByEmail(@PathVariable String email);
    Page<User> findAll(Pageable pageable);
}
