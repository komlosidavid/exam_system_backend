
package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.TokenDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * Service class for handling logout requests.
 */
@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {

    /**
     * Reference of {@link TokenDao}.
     */
    private final TokenDao tokenRepository;

    /**
     * Method to handle logout requests.
     * @param request is the {@link HttpServletRequest}.
     * @param response is the {@link HttpServletResponse}.
     * @param authentication is the {@link Authentication}.
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader =
                request.getHeader(HttpHeaders.AUTHORIZATION);
        final String accessToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        accessToken = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(accessToken)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
