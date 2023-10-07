
package inf.unideb.hu.exam.system.response;

import inf.unideb.hu.exam.system.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response for authentication.
 */
@AllArgsConstructor
@Getter
public class AuthenticationResponse {

    /**
     * Access token of the authentication.
     */
    private String accessToken;

    /**
     * Refresh token of the authentication.
     */
    private String refreshToken;

    /**
     * {@link UserDto} object of the authentication.
     */
    private UserDto user;
}
