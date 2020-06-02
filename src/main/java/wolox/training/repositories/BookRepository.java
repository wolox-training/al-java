package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Book;
import wolox.training.models.User;

public interface BookRepository extends JpaRepository<Book,Long> {

    /**
     *
     * @param author (Method will filter by Books Author)
     * @return {@link Optional< Book >} (Will return a Book or void)
     */
    Optional<Book> findFirstByAuthor(final String author);
}
