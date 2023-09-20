package inf.unideb.hu.exam.system.service;

import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.request.AuthenticationRequest;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface AuthenticationService {

    Pair<Optional<AuthenticationResponse>>
    register(CreateUserEntityRequest request);
    AuthenticationResponse authenticate(
            AuthenticationRequest request);
    Pair<Optional<AuthenticationResponse>>
    refreshToken(HttpServletRequest request,
                 HttpServletResponse response) throws IOException;
}
