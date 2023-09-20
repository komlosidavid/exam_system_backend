package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.TokenDao;
import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.models.Pair;
import inf.unideb.hu.exam.system.models.Token;
import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.request.AuthenticationRequest;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.response.AuthenticationResponse;
import inf.unideb.hu.exam.system.security.token.TokenService;
import inf.unideb.hu.exam.system.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao repository;
    private final TokenDao tokenRepository;
    private final PasswordEncoder encoder;
    private final TokenService jwtService   ;
    private final AuthenticationManager manager;

    @Override
    public Pair<Optional<AuthenticationResponse>> register(
            CreateUserEntityRequest request) {
        Optional<User> userOptional = repository.
                findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            return new Pair<>(Optional.empty(),
                    "User with this username was found!");
        }

        userOptional = repository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            return new Pair<>(
                    Optional.empty(),
                    "User with this email was found!");
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);

        var accessToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, accessToken);

        return new Pair<>(Optional.of(
                new AuthenticationResponse(accessToken, refreshToken)),
                null);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));

        var user = repository.findByUsername(request.getUsername());

        assert user.isPresent();

        var accessToken = jwtService.generateJwtToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(user.get());
        revokeAllUserTokens(user.get());
        saveUserToken(user.get(), accessToken);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public Pair<Optional<AuthenticationResponse>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        final String authHeader =

                request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new Pair<>(Optional.empty(), "Forbidden!");
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.repository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateJwtToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse =
                        new AuthenticationResponse(accessToken, refreshToken);
                return new Pair<>(Optional.of(authResponse), null);
                //new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

        return new Pair<>(Optional.empty(), "Internal server error!");
    }

    private void saveUserToken(User savedUser, String accessToken) {
        var token = Token.builder()
                .token(accessToken)
                .user(savedUser)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.
                findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
