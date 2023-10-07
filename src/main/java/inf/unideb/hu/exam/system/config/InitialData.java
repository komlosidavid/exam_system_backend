package inf.unideb.hu.exam.system.config;

import inf.unideb.hu.exam.system.dao.UserDao;
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

    private final UserDao repository;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void createInitialUsers() {
        var user = User.builder()
                .firstname("Dávid")
                .lastname("Komlósi")
                .username("komlosidavid")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.ADMIN)
                .build();
        user.createFullName();

        repository.save(user);

        user = User.builder()
                .firstname("Aladár")
                .lastname("Zsenge")
                .username("aladar")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.TEACHER)
                .build();
        user.createFullName();

        repository.save(user);

        user = User.builder()
                .firstname("Zsolt")
                .lastname("Kalapás")
                .username("kalapas")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.TEACHER)
                .build();
        user.createFullName();

        repository.save(user);

        user = User.builder()
                .firstname("András")
                .lastname("Kemény")
                .username("kemeny")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.TEACHER)
                .build();
        user.createFullName();

        repository.save(user);

        user = User.builder()
                .firstname("László")
                .lastname("Trombitás")
                .username("trombitas")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.TEACHER)
                .build();
        repository.save(user);
        user.createFullName();

        user = User.builder()
                .firstname("Adorján")
                .lastname("Gellért")
                .username("geller")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.TEACHER)
                .build();
        user.createFullName();

        repository.save(user);

        user = User.builder()
                .firstname("Károly")
                .lastname("Mihaszna")
                .username("mihaszna")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.TEACHER)
                .build();
        user.createFullName();

        repository.save(user);

        user = User.builder()
                .firstname("Adél")
                .lastname("Kovács")
                .username("kovacs")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.STUDENT)
                .build();
        user.createFullName();

        repository.save(user);

        user = User.builder()
                .firstname("Péter")
                .lastname("Bátor")
                .username("bator")
                .email("example@email.com")
                .password(encoder.encode("password"))
                .role(Role.STUDENT)
                .build();
        user.createFullName();

        repository.save(user);
    }
}
