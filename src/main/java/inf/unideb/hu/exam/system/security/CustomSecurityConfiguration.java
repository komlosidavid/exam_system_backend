
package inf.unideb.hu.exam.system.security;

import inf.unideb.hu.exam.system.security.token.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Configuration class for configure application security.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfiguration {

    /**
     * Reference of {@link TokenAuthenticationFilter}.
     */
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * Reference of {@link AuthenticationProvider}.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Reference of {@link CustomCorsConfiguration}.
     */
    private final CustomCorsConfiguration corsConfiguration;

    /**
     * Reference of {@link LogoutHandler}.
     */
    private final LogoutHandler logoutHandler;

    /**
     * Function to configure application security.
     * @param http of the application {@link HttpSecurity}.
     * @return a {@link SecurityFilterChain}.
     * @throws Exception for error handling.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/register",
                                "/api/v1/auth/authenticate")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(tokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfiguration
                        .corsConfigurationSource()))
                .logout(
                        logout -> logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                        ((request,
                                          response,
                                          authentication) ->
                                                SecurityContextHolder.clearContext())
                                )
                );

        return http.build();
    }
}
