
package inf.unideb.hu.exam.system.controller;

import inf.unideb.hu.exam.system.dto.TestDto;
import inf.unideb.hu.exam.system.models.ResponseMessage;
import inf.unideb.hu.exam.system.request.CreateTestEntityRequest;
import inf.unideb.hu.exam.system.service.impl.TestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller class for handling requests from the client.
 */
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
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
     * Function to create a new {@link inf.unideb.hu.exam.system.models.Test} entity.
     * @param request is the payload.
     * @return a new {@link ResponseEntity} with the created entity data.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createTestEntity(
            @RequestBody CreateTestEntityRequest request) {
        var creationResponse = service.createTest(request);

        if (creationResponse.getValue().isPresent()) {
            var testEntityDto = modelMapper
                    .map(creationResponse.getValue().get(),
                            TestDto.class);

            return ResponseEntity.ok(testEntityDto);
        }

        return new ResponseEntity<>(
                new ResponseMessage(creationResponse.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Function to get all the test entities.
     * @return a list of {@link inf.unideb.hu.exam.system.models.Test} entities.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE
    )
    public Page<TestDto> getAllTests(Pageable pageable) {
        var entityPage = service.getAllTests(pageable);

        return entityPage.map(entity -> modelMapper.map(entity, TestDto.class));
    }

}
