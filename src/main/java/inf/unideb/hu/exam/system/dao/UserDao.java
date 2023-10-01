
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
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



    Optional<User> findByUsername(@Param("username") String username);
    Optional<User> findByEmail(@Param("email") String email);
    Page<User> findAll(Pageable pageable);
    Page<User> findAllByRole(@Param("role") Role role, Pageable pageable);
}
