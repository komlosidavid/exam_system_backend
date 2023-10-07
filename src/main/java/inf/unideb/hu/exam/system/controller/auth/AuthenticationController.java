package inf.unideb.hu.exam.system.controller.auth;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.ResponseMessage;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.request.AuthenticationRequest;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.response.AuthenticationResponse;
import inf.unideb.hu.exam.system.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller class for handling authentication requests.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    /**
     * Reference for {@link AuthenticationServiceImpl} class.
     */
    private final AuthenticationServiceImpl service;

    /**
     * Function to register a new {@link User} entity.
     * @param request a {@link CreateUserEntityRequest} for create a new {@link User} entity.
     * @return a {@link ResponseEntity} object of {@link AuthenticationResponse}.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            path = "/register"
    )
    public ResponseEntity<?> register (
            @RequestBody CreateUserEntityRequest request) {
        Pair<Optional<AuthenticationResponse>> registerResponse =
                service.register(request);

        if (registerResponse.getValue().isEmpty()) {
            return new ResponseEntity<>(
                    new ResponseMessage(registerResponse.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(registerResponse.getValue().get());
    }

    /**
     * Function to authenticate a {@link User}.
     * @param request of {@link AuthenticationRequest}.
     * @return a {@link ResponseEntity} of {@link AuthenticationResponse}.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            path = "/authenticate"
    )
    public ResponseEntity<?> authenticate (
            @RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse authenticate =
                    service.authenticate(request);
            return ResponseEntity.ok(authenticate);
        } catch (BadCredentialsException exception) {
            log.warn("User passed wrong credentials!");
            return new ResponseEntity<>(
                    new ResponseMessage(exception.getMessage()),
                    HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Function to create new access token from the refresh token.
     * @param request a {@link HttpServletRequest} object.
     * @param response a {@link HttpServletResponse} object.
     * @return a {@link ResponseEntity} with {@link AuthenticationResponse}.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE,
            path = "/refresh"
    )
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        var authResponse = service.refreshToken(request, response);

        if (authResponse.getValue().isPresent()) {
            log.info("Access token generated from refresh token!");
            return ResponseEntity.ok(authResponse.getValue().get());
        }

        if (authResponse.getMessage().equals("Forbidden!")) {
            log.warn("Getting access token from refresh token was forbidden!");
            return new ResponseEntity<>(
                    new ResponseMessage(authResponse.getMessage()),
                    HttpStatus.FORBIDDEN);
        }
        else {
            log.error("Internal server error!");
            return new ResponseEntity<>(
                    new ResponseMessage(authResponse.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
