
package inf.unideb.hu.exam.system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class for returning JSON data to the client.
 */
@AllArgsConstructor
@Getter
public class ResponseMessage {

    /**
     * The returning message.
     */
    private String message;
}
