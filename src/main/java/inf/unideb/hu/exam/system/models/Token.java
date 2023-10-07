
package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import inf.unideb.hu.exam.system.models.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

/**
 * Token entity for holding token datas.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Token {

    /**
     * Primary key property.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Token property.
     */
    @Column(unique = true)
    private String token;

    /**
     * {@link TokenType} property.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private final TokenType tokenType = TokenType.BEARER;

    /**
     * Revoked property.
     */
    @Builder.Default
    private boolean revoked = false;

    /**
     * Expired property.
     */
    @Builder.Default
    private boolean expired = false;

    /**
     * {@link User} property.
     */
    @ManyToOne
    @JsonBackReference
    private User user;
}
