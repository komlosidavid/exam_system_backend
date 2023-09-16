
package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class for implementing service methods.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * User dao dependency injection.
     */
    private final UserDao repository;

    /**
     * Function to create a new {@link User} entity.
     *
     * @param request for creating {@link User} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    @Override
    public Pair<Optional<User>> createNewUserEntity(CreateUserEntityRequest request) {
        var newUserEntity = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .build();

        repository.save(newUserEntity);

        return new Pair<>(Optional.of(newUserEntity), null);
    }
}
