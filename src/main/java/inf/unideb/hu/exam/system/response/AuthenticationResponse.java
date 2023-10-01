package inf.unideb.hu.exam.system.response;

import inf.unideb.hu.exam.system.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private UserDto user;
}
