package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.User;

public interface UserRepository extends JpaRepository<User,Long> {

    /**
     *
     * @param username (Method will filter by username)
     * @return {@link Optional<User>} (Will return an User or void)
     */
    Optional<User> findFirstByUsername(final String username);
}
