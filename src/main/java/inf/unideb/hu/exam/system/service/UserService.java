
package inf.unideb.hu.exam.system.service;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;

import java.util.Optional;

/**
 * Interface for handling user service methods and functions.
 */
public interface UserService {

    /**
     * Function to create a new {@link User} entity.
     * @param request for creating {@link User} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    Pair<Optional<User>> createNewUserEntity(CreateUserEntityRequest request);
}
