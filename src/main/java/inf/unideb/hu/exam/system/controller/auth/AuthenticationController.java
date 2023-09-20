package inf.unideb.hu.exam.system.controller.auth;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.ResponseMessage;
import inf.unideb.hu.exam.system.request.AuthenticationRequest;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.response.AuthenticationResponse;
import inf.unideb.hu.exam.system.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

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
            return new ResponseEntity<>(
                    new ResponseMessage(exception.getMessage()),
                    HttpStatus.FORBIDDEN);
        }
    }

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
            return ResponseEntity.ok(authResponse.getValue().get());
        }

        if (authResponse.getMessage().equals("Forbidden!")) {
            return new ResponseEntity<>(
                    new ResponseMessage(authResponse.getMessage()),
                    HttpStatus.FORBIDDEN);
        }
        else {
            return new ResponseEntity<>(
                    new ResponseMessage(authResponse.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
