
package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import inf.unideb.hu.exam.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<User> getAllUsersByRole(Role role, Pageable pageable) {
        return repository.findAllByRole(role, pageable);
    }

    @Override
    public Pair<Optional<User>> getUserById(UUID id) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            return new Pair<>(userOptional, null);
        }

        return new Pair<>(Optional.empty(), "User was not found!");
    }

}
