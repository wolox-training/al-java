package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book,Long> {

    /**
     * Returns the first Book that it finds filtered by its author
     * @param author (Method will filter by Books Author)
     * @return {@link Optional< Book >} (Will return a Book or void)
     */
    Optional<Book> findFirstByAuthor(final String author);
}
