
package inf.unideb.hu.exam.system.service;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface for handling user service methods and functions.
 */
public interface UserService {

    Page<User> getAllUsers(Pageable pageable);

    Pair<Optional<User>> getUserById(UUID id);
}
