
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO interface for implementing database manipulation
 * methods and function using Spring Data JPA.
 */
@Repository
public interface UserDao extends JpaRepository<User, UUID> {
}