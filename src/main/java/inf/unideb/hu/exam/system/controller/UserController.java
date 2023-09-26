package inf.unideb.hu.exam.system.controller;

import inf.unideb.hu.exam.system.dto.UserDto;
import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.ResponseMessage;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl service;
    private final ModelMapper modelMapper;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE
    )
    public Page<UserDto> getAllUsers(Pageable pageable) {
        var result = service.getAllUsers(pageable);
        return result.map(user ->
                modelMapper.map(user, UserDto.class));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE,
            path = "{id}"
    )
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        Pair<Optional<User>> serviceResponse = service.getUserById(id);

        if (serviceResponse.getValue().isPresent()) {
            return new ResponseEntity<>(modelMapper
                    .map(serviceResponse.getValue().get(),
                            UserDto.class), HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new ResponseMessage(serviceResponse.getMessage()),
                HttpStatus.NOT_FOUND);
     }

}
