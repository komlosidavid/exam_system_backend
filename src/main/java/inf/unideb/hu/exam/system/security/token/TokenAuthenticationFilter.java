package inf.unideb.hu.exam.system.security.token;

import inf.unideb.hu.exam.system.models.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Configuration class for configure token authentication.
 */
@Configuration
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Reference for the {@link TokenService}.
     */
    private final TokenService jwtService;

    /**
     * Reference for the {@link UserDetailsService}.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Method to authenticate a {@link HttpServletRequest} if it has a valid {@link Token} or not.
     * @param request of {@link HttpServletRequest}.
     * @param response of {@link HttpServletResponse}.
     * @param filterChain for filtering the {@link HttpServletRequest}.
     * @throws ServletException for exception handling.
     * @throws IOException for exception handling.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request
                .getHeader("Authorization");
        final String token;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        }
        else {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);

            if (username != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {
                UserDetails userDetails =
                        this.userDetailsService
                                .loadUserByUsername(username);
                if (jwtService.isJwtTokenValid(
                        token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
