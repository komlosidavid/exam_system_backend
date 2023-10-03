package inf.unideb.hu.exam.system.security.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService jwtService;
    private final UserDetailsService userDetailsService;
    private RequestAttributeSecurityContextRepository repository =
            new RequestAttributeSecurityContextRepository();

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
            repository.saveContext(SecurityContextHolder.getContext(),
                    request, response);
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
                    repository.saveContext(SecurityContextHolder.getContext(),
                            request, response);
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
