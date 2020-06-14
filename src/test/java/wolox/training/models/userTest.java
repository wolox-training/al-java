package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class userTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("marihe", "Mary Heize", LocalDate.of(1995,03,11));
    }

    @Test
    public void whenFindUserByUsername_thenReturnsCorrectUser() {
        entityManager.persist(user);
        Optional<User> marihe = userRepository.findFirstByUsername(user.getUsername());
        assertThat(marihe.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSetsNameNull_throwsCorrectException() {
        assertThrows(NullPointerException.class, () -> user.setName(null));
    }

    @Test
    public void whenSetsUsernameNull_throwsCorrectException() {
        assertThrows(NullPointerException.class, () -> user.setUsername(null));
    }

    @Test
    public void whenSetsUsernameEmpty_throwsCorrectException() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
    }

    @Test
    public void whenSetsBirthdayAfterToday_throwsCorrectException() {
        assertThrows(IllegalArgumentException.class, () -> user.setBirthDate(LocalDate.now().plusDays(14)));
    }

    @Test
    public void whenAddBookToUser_returnsUserWithAddedBook()
        throws BookAlreadyOwnedException {
        Book theMist = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "143565786");
        user.addBook(theMist);
        assertTrue(() -> user.getBooks().contains(theMist));
    }

    @Test
    public void whenAddBookThatUserAlreadyHas_throwsCorrectException()
        throws BookAlreadyOwnedException {
        Book theMist = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "143565786");
        user.addBook(theMist);
        assertThrows(BookAlreadyOwnedException.class, () -> user.addBook(theMist));
    }

    @Test
    public void whenRemoveBookOfUser_returnsUserWithoutRemovedBook()
        throws BookAlreadyOwnedException, BookNotFoundException {
        Book theMist = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "143565786");
        user.addBook(theMist);
        user.removeBook(theMist);
        assertFalse(() -> user.getBooks().contains(theMist));
    }

    @Test
    public void whenRemoveBookThatUserHasNot_throwsCorrectException()
        throws BookAlreadyOwnedException, BookNotFoundException {
        Book theMist = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "143565786");
        user.addBook(theMist);
        user.removeBook(theMist);
        assertThrows(BookNotFoundException.class, () -> user.removeBook(theMist));
    }
}
