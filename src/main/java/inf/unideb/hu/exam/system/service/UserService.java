
package inf.unideb.hu.exam.system.service;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

/**
 * Interface for handling user service methods and functions.
 */
public interface UserService {

    /**
     * Function to get all of the {@link User} entities.
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} of {@link User} entities.
     */
    Page<User> getAllUsers(Pageable pageable);

    /**
     * Function to get all of the {@link User} entities by the requested {@link Role}.
     * @param role is the requested {@link Role}.
     * @param pageable is a {@link Pageable} object.
     * @return a {@link Page} of {@link User} entities.
     */
    Page<User> getAllUsersByRole(Role role, Pageable pageable);

    /**
     * Function to get a {@link User} by id property.
     * @param id of the {@link User}.
     * @return a {@link Pair} of {@link Optional} {@link User}.
     */
    Pair<Optional<User>> getUserById(UUID id);

    /**
     * Function to get {@link User} entities by fullname property.
     * @param name of the {@link User}.
     * @return a {@link List} of {@link User} entities.
     */
    List<User> getUsersByName(String name);

    /**
     * Function to get {@link User} entities by name and {@link Role}.
     * @param name of the {@link User}.
     * @param role of the {@link User}.
     * @return a {@link List} of {@link User} entities.
     */
    List<User> getUsersByNameAndRole(String name, String role);

    /**
     * Function to get all {@link User} entities which has an id in the data.
     * @param data for holding UUIDs.
     * @return a {@link List} of {@link User} entities.
     */
    List<User> getAllUsersByUUIDList(List<UUID> data);
}
