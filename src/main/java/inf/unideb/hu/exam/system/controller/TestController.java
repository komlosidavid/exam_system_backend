
package inf.unideb.hu.exam.system.controller;

import inf.unideb.hu.exam.system.dto.TestDto;
import inf.unideb.hu.exam.system.models.GetAllTestsFilter;
import inf.unideb.hu.exam.system.models.Test;
import inf.unideb.hu.exam.system.request.CreateTestEntityRequest;
import inf.unideb.hu.exam.system.security.token.TokenService;
import inf.unideb.hu.exam.system.service.impl.TestServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller class for handling requests from the client.
 */
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    /**
     * Test service dependency injection.
     */
    private final TestServiceImpl service;
    /**
     * Model mapper dependency injection.
     */
    private final ModelMapper modelMapper;

    /**
     * Reference for {@link TokenService}.
     */
    private final TokenService jwtService;

    /**
     * Function to create a new {@link Test} entity.
     * @param request is the payload.
     * @return a new {@link ResponseEntity} with the created entity data.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> createTestEntity(
            @RequestBody CreateTestEntityRequest request) {
        return ResponseEntity.status(service.createTest(request)).build();
    }

    /**
     * Function to get a {@link Test} entity by id.
     * @param id of the {@link Test}.
     * @return a {@link ResponseEntity} containing the {@link Test} entity.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE,
            path = "/test/{id}"
    )
    public ResponseEntity<?> getTestById(@PathVariable UUID id) {
        var result = service.getTestById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(result.get(), TestDto.class));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Controller function to get all test
     * entities for the corresponding request.
     * @param request a {@link HttpServletRequest}.
     * @param filter of {@link GetAllTestsFilter}.
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} object containing {@link TestDto} objects.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE,
            path = "/{filter}"
    )
    public Page<TestDto> getAllTests(
            HttpServletRequest request,
            @PathVariable String filter,
            Pageable pageable) {
        final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token = headers.substring(7);
        final String username = jwtService.extractUsername(token);

        if (username != null) {
            var entityPage = service.getAllTests(username, GetAllTestsFilter.valueOf(filter), pageable);

            return entityPage.map(entity -> modelMapper.map(entity, TestDto.class));
        }

        log.error("User not authenticated!");
        return Page.empty();
    }

    /**
     * Function to get all {@link Test} entities by subject property.
     * @param request for the {@link HttpServletRequest}.
     * @param subject of the {@link Test}.
     * @return a {@link ResponseEntity} containing the result {@link Test} entities.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE,
            path = "/get"
    )
    public ResponseEntity<?> getAllTestsBySubjectName(HttpServletRequest request,
                                                      @RequestParam(name = "subject") String subject) {
        final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token = headers.substring(7);
        final String username = jwtService.extractUsername(token);

        if (username != null) {
            var result = service.getAllTestsWithSubjectName(username, subject);
            var mapped = result.stream().map(test -> modelMapper.map(test, TestDto.class)).toList();
            return ResponseEntity.ok(mapped);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
