
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity class for holding user infos.
 */
@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    /**
     * UUID based primary key.
     */
    @Id
    @GeneratedValue
    private UUID id;
    /**
     * First name of the user.
     */
    @JsonIgnore
    private String firstname;
    /**
     * Last name of the user.
     */
    @JsonIgnore
    private String lastname;

    /**
     * Function to get the full name of the user.
     * @return the user's full name.
     */
    @JsonProperty
    public String getFullname() {
        return lastname + " " + firstname;
    }
}
