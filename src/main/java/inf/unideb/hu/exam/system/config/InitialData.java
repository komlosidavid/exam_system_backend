package inf.unideb.hu.exam.system.config;

import inf.unideb.hu.exam.system.models.User;
import inf.unideb.hu.exam.system.models.enums.Role;
import inf.unideb.hu.exam.system.request.CreateUserEntityRequest;
import inf.unideb.hu.exam.system.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("h2")
@Configuration
@RequiredArgsConstructor
public class InitialData {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authService;

    @PostConstruct
    public void createInitialUsers() {
        var userRequest = new CreateUserEntityRequest();
        userRequest.setFirstname("Dávid");
        userRequest.setLastname("Komlósi");
        userRequest.setUsername("komlosidavid");
        userRequest.setEmail("komlosidavid19990410@gmail.com");
        userRequest.setPassword("Krumplisteszta18");
        userRequest.setRole(Role.ADMIN);
        authService.register(userRequest);

        var user = User.builder()
                .firstname("Józsi")
                .lastname("Valaki")
                .username("valakijozsi")
                .email("example@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.TEACHER)
                .build();

        user = User.builder()
                .firstname("Teréz")
                .lastname("Vár")
                .username("varteri")
                .email("example@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.TEACHER)
                .build();

        user = User.builder()
                .firstname("Balázs")
                .lastname("Kovács")
                .username("kovacsbalazs")
                .email("example@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.TEACHER)
                .build();

        user = User.builder()
                .firstname("András")
                .lastname("Solymosi")
                .username("solymosiandras")
                .email("example@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.STUDENT)
                .build();

        user = User.builder()
                .firstname("Lajos")
                .lastname("Aladár")
                .username("aladarlajos")
                .email("example@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.STUDENT)
                .build();



    }
}
