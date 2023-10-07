package inf.unideb.hu.exam.system.controller;

import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.dto.UserDto;
import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.ResponseMessage;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import inf.unideb.hu.exam.system.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller class for handling {@link User} entity operations.
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    /**
     * Reference of {@link UserServiceImpl} class.
     */
    private final UserServiceImpl service;
    /**
     * Reference of {@link ModelMapper} object.
     */
    private final ModelMapper modelMapper;

    /**
     * Function to get all {@link User} entities.
     * @param pageable a {@link Pageable} object.
     * @return a {@link Page} object containing {@link UserDto} objects.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE
    )
    public Page<UserDto> getAllUsers(Pageable pageable) {
        var result = service.getAllUsers(pageable);
        return result.map(user ->
                modelMapper.map(user, UserDto.class));
    }

    /**
     * Function to get all {@link User} entities according to the request.
     * @param id of the {@link User} entity.
     * @param role of the {@link User} entity.
     * @param fullname of the {@link User} entity.
     * @param pageable a {@link Pageable} object.
     * @return a {@link ResponseEntity} object with the corresponding result.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE,
            path = "/get"
    )
    public ResponseEntity<?> getUser(@RequestParam(name = "id", required = false) UUID id,
                                     @RequestParam(name = "role", required = false) String role,
                                     @RequestParam(name = "fullname", required = false) String fullname,
                                     Pageable pageable) {
        // If the id was given get the corresponding user.
        if (id != null) {
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
        // If role was given return all the users with that role.
        else if (role != null) {
            var result = service.getAllUsersByRole(Role.valueOf(role), pageable);
            return new ResponseEntity<>(
                    result.map(user -> modelMapper.map(user, UserDto.class)),
                    HttpStatus.OK);
        }
        else if (fullname != null) {
            var result = service.getUsersByFullName(fullname.toLowerCase());
            List<UserDto> mapped = result.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .toList();
            return ResponseEntity.ok(mapped);
        }
        // If nothing was given.
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
