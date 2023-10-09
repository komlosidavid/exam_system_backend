
package inf.unideb.hu.exam.system.service;

import inf.unideb.hu.exam.system.models.GetAllTestsFilter;
import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.Test;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.request.CreateTestEntityRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for handling test service methods and functions.
 */
public interface TestService {

    /**
     * Get all test entities from the database.
     * @return a {@link List} of {@link Test} entities.
     */
    Page<Test> getAllTests(String username,
                           GetAllTestsFilter filter,
                           Pageable pageable);

    /**
     * Function to create a {@link Test} entity.
     * @param request for creating {@link Test} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    HttpStatus createTest(CreateTestEntityRequest request);

    /**
     * Function to get all {@link Test} entities by subject property.
     * @param username of the {@link User}.
     * @param subject of the {@link Test}.
     * @return a {@link List} of {@link Test} entities.
     */
    List<Test> getAllTestsWithSubjectName(String username, String subject);

    /**
     * Function to get a {@link Test} entity by id.
     * @param id of the {@link Test}.
     * @return an {@link Optional} of {@link Test} entity.
     */
    Optional<Test> getTestById(UUID id);
}
