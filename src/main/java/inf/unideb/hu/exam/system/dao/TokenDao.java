
package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.Token;
import inf.unideb.hu.exam.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository class for handling CRUD operations for {@link Token} entity.
 */
@Repository
public interface TokenDao extends JpaRepository<Token, UUID> {

    /**
     * Function to get all the valid {@link Token} entities by the corresponding {@link User} entities.
     * @param id of the {@link User} entity.
     * @return a {@link List} of the {@link Token} entities.
     */
    @Query(value = """
        select t from Token t
        inner join User u\s
        on t.user.id = u.id\s
        where u.id = :id and
        (t.expired = false or t.revoked = false)\s
    """)
    List<Token> findAllValidTokenByUser(UUID id);

    /**
     * Function to get a {@link Token} from the database.
     * @param token the {@link Token} entity token property.
     * @return an {@link Optional} of the {@link Token} entity.
     */
    Optional<Token> findByToken(String token);
}
