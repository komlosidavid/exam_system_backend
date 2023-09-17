
package inf.unideb.hu.exam.system.service;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.Test;
import inf.unideb.hu.exam.system.request.CreateTestEntityRequest;
import inf.unideb.hu.exam.system.request.UpdateTestEntityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<Test> getAllTests(Pageable pageable);

    /**
     * Function to create a {@link Test} entity.
     * @param request for creating {@link Test} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    Pair<Optional<Test>> createTest(CreateTestEntityRequest request);

    /**
     * Function to update a {@link Test} entity.
     * @param id for identify the {@link Test} entity.
     * @param request for updating {@link Test} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    Pair<Optional<Test>> updateTest(UUID id, UpdateTestEntityRequest request);
}
