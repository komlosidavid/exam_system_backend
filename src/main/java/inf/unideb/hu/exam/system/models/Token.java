package inf.unideb.hu.exam.system.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import inf.unideb.hu.exam.system.models.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Token {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private final TokenType tokenType = TokenType.BEARER;
    @Builder.Default
    private boolean revoked = false;
    @Builder.Default
    private boolean expired = false;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
