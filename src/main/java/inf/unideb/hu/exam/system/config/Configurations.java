
package inf.unideb.hu.exam.system.config;

import inf.unideb.hu.exam.system.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for configure initial user details on profile h2 only.
 */
@Configuration
@RequiredArgsConstructor
public class Configurations {

    /**
     * {@link User} entities JPA repository.
     */
    private final UserDao repository;

    /**
     * Declaring base {@link UserDetailsService}.
     * @return a {@link UserDetailsService} reference.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User was not found!"));
    }

    /**
     * Declaring base {@link  AuthenticationProvider}.
     * @return an {@link AuthenticationProvider} reference.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    /**
     * Declaring base {@link PasswordEncoder} class.
     * @return a {@link PasswordEncoder} reference.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Declaring base {@link AuthenticationManager} class.
     * @param configuration properties.
     * @return an {@link AuthenticationManager} reference.
     * @throws Exception if the creation was unsuccessful.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Declaring the base {@link ModelMapper}.
     * @return a {@link ModelMapper} reference.
     */
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
