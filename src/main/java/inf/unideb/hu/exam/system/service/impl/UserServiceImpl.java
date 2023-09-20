
package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

}
