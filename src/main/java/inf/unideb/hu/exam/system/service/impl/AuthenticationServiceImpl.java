
package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.TokenDao;
import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.dto.UserDto;
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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for authentications.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Reference of {@link UserDao}.
     */
    private final UserDao repository;

    /**
     * Reference of {@link TokenDao}.
     */
    private final TokenDao tokenRepository;

    /**
     * Reference of {@link ModelMapper}.
     */
    private final ModelMapper modelMapper;

    /**
     * Reference of {@link PasswordEncoder}.
     */
    private final PasswordEncoder encoder;

    /**
     * Reference of {@link TokenService}.
     */
    private final TokenService jwtService;

    /**
     * Reference of {@link AuthenticationManager}.
     */
    private final AuthenticationManager manager;

    /**
     * Function for register request.
     * @param request is a {@link CreateUserEntityRequest}.
     * @return a {@link Pair} of {@link Optional} {@link AuthenticationResponse}.
     */
    @Override
    public Pair<Optional<AuthenticationResponse>> register(CreateUserEntityRequest request) {
        Optional<User> userOptional = repository.
                findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            log.warn("User with username " + request.getUsername() + " was found and cannot be created!");
            return new Pair<>(Optional.empty(),
                    "User with this username was found!");
        }

        userOptional = repository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            log.warn("User with email " + request.getEmail() + " was found and cannot be created!");
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

        log.info("User was created with id " + savedUser.getId());
        return new Pair<>(Optional.of(
                new AuthenticationResponse(accessToken, refreshToken,
                        modelMapper.map(user, UserDto.class))),
                null);
    }

    /**
     * Function to authenticate a {@link User}.
     * @param request is a {@link AuthenticationRequest}.
     * @return a new {@link AuthenticationResponse}.
     */
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

        log.info("User was authenticated with username: " + user.get().getUsername());

        return new AuthenticationResponse(accessToken, refreshToken,
                modelMapper.map(user.get(), UserDto.class));
    }

    /**
     * Function to get a new access token using the refresh token.
     * @param request  of {@link HttpServletRequest}.
     * @param response of {@link HttpServletResponse}.
     * @return a {@link Pair} of {@link Optional} {@link AuthenticationResponse}.
     */
    @Override
    public Pair<Optional<AuthenticationResponse>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader =

                request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Token not found or not acceptable!");
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
                        new AuthenticationResponse(accessToken, refreshToken,
                                modelMapper.map(user, UserDto.class));
                return new Pair<>(Optional.of(authResponse), null);
            }
        }

        log.error("Internal server error!");
        return new Pair<>(Optional.empty(), "Internal server error!");
    }

    /**
     * Method for save a {@link User} {@link Token} after authentication.
     * @param savedUser of the {@link User} entity.
     * @param accessToken is the token.
     */
    private void saveUserToken(User savedUser, String accessToken) {
        var token = Token.builder()
                .token(accessToken)
                .user(savedUser)
                .build();
        var newToken = tokenRepository.save(token);
        log.info("Token was created with id " + newToken.getId());
    }

    /**
     * Method to revoke all tokens for the {@link User}.
     * @param user is the {@link User}.
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.
                findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            log.error("No valid tokens were found!");
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
        log.info("Tokens were revoked for user " + user.getUsername());
    }
}
