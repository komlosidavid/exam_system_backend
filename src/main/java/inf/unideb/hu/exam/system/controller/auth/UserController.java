
package inf.unideb.hu.exam.system.controller.auth;

import inf.unideb.hu.exam.system.dto.UserDto;
import inf.unideb.hu.exam.system.models.ResponseMessage;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

/**
 * Controller class for handling requests from the client.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    /**
     * User service dependency injection.
     */
    private final UserServiceImpl service;
    /**
     * Model mapper dependency injection.
     */
    private final ModelMapper modelMapper;

    /**
     * Function to create a new {@link User} entity.
     * @param request is the payload.
     * @return a {@link ResponseEntity} with the response message
     * or with the created entity data.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createNewUserEntity(@RequestBody CreateUserEntityRequest request) {
        var creationResponse = service.createNewUserEntity(request);

        if (creationResponse.getValue().isPresent()) {
            var userDto = modelMapper.map(creationResponse.getValue().get(), UserDto.class);
            return ResponseEntity.ok(userDto);
        }

        return new ResponseEntity<>(
                new ResponseMessage(creationResponse.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
