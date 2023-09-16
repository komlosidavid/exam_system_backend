
package inf.unideb.hu.exam.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

/**
 * Class for handling more than one message back from the service layer
 * if it is needed with the generic parameter.
 * @param <T> should be an {@link java.util.Optional}.
 */
@AllArgsConstructor
@Data
public class Pair <T> {

    /**
     * Pair value which contains the data.
     */
    private T value;
    /**
     * String message which contains the response message.
     */
    private String message;
}
