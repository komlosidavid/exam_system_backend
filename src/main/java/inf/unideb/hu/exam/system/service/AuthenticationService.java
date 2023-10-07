
package inf.unideb.hu.exam.system.service;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.request.AuthenticationRequest;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Interface for authentication service.
 */
public interface AuthenticationService {

    /**
     * Function for register request.
     * @param request is a {@link CreateUserEntityRequest}.
     * @return a {@link Pair} of {@link Optional} {@link AuthenticationResponse}.
     */
    Pair<Optional<AuthenticationResponse>>
    register(CreateUserEntityRequest request);

    /**
     * Function to authenticate a {@link User}.
     * @param request is a {@link AuthenticationRequest}.
     * @return a new {@link AuthenticationResponse}.
     */
    AuthenticationResponse authenticate(
            AuthenticationRequest request);

    /**
     * Function to get a new access token using the refresh token.
     * @param request of {@link HttpServletRequest}.
     * @param response of {@link HttpServletResponse}.
     * @return a {@link Pair} of {@link Optional} {@link AuthenticationResponse}.
     * @throws IOException for error handling.
     */
    Pair<Optional<AuthenticationResponse>>
    refreshToken(HttpServletRequest request,
                 HttpServletResponse response) throws IOException;
}
