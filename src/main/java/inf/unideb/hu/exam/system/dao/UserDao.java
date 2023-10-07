
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

/**
 * DAO interface for implementing database manipulation
 * methods and function using Spring Data JPA.
 */
@Repository
public interface UserDao extends JpaRepository<User, UUID> {

    /**
     * Function to get a {@link User} entity by username.
     * @param username of the {@link User} entity.
     * @return an {@link Optional} of the result {@link User} entity.
     */
    Optional<User> findByUsername(@Param("username") String username);

    /**
     * Function to get a {@link User} entity by email property.
     * @param email of the {@link User} entity.
     * @return an {@link Optional} of the result {@link User} entity.
     */
    Optional<User> findByEmail(@Param("email") String email);

    /**
     * Function to get all {@link User} entities.
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} of the result {@link User} entities.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Function to fing all {@link User} entities by {@link Role} property.
     * @param role {@link Role} property of the {@link User} entity.
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} of the result {@link User} entities.
     */
    Page<User> findAllByRole(@Param("role") Role role, Pageable pageable);

    /**
     * Function to find all the {@link User} entities by full name.
     * @param name of the {@link User}.
     * @return a {@link List} of {@link User} entities.
     */
    @Query("""
        SELECT u 
            FROM User u 
            WHERE LOWER(u.firstname) LIKE %:name% OR 
            LOWER(u.lastname) LIKE %:name%
    """)
    List<User> findAllByFullNameContains(String name);
}
