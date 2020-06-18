package wolox.training.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    /**
     * Returns first user by id or throws an exception if doesn't found it
     * @param id (id of user)
     * @return {@link User} (The first user that it finds)
     * @throws UserNotFoundException (If it can't find a user by this id)
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
    }

    /**
     * Updates a specific user
     * @param id (id of user)
     * @param user (user's fields whose will be used to update it)
     * @return {@link User} (updated user)
     * @throws UserNotFoundException (If it can't find a user by this id)
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException{
        userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
        return userRepository.save(user);
    }

    /**
     * Deletes user by id
     * @param id (id of user)
     * @throws UserNotFoundException (If it can't find a user by this id)
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws UserNotFoundException {
        userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
        userRepository.deleteById(id);
    }

    /**
     * Creates a new user
     * @param user (user to be created)
     * @return {@link User} (created user)
     */
    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    /**
     * Returns all users
     * @return {@link List<User>} (All users)
     */
    @GetMapping("/all")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    /**
     * Detaches existing book from user
     * @param book_id (the id of the book that we try to detach)
     * @param user_id (the id of the user we want)
     * @throws UserNotFoundException (If it can't find a user by this id)
     * @throws BookNotFoundException (if book isn't found)
     */
    @DeleteMapping("/detach/{user_id}/{book_id}")
    public void detachBook(@PathVariable Long book_id, @PathVariable Long user_id)
        throws UserNotFoundException, BookNotFoundException{
        User user = userRepository.findById(user_id)
            .orElseThrow(() -> new UserNotFoundException(user_id.toString(), "id"));
        Book book = bookRepository.findById(book_id)
            .orElseThrow(() -> new BookNotFoundException(book_id.toString(), "id"));
        user.removeBook(book);
    }

    /**
     * Attaches a book to an user
     * @param book_id (the id of the book that we try to attach)
     * @param user_id (the id of the user we want)
     * @throws UserNotFoundException (If it can't find a user by this id)
     * @throws BookNotFoundException (if book isn't found)
     * @throws BookAlreadyOwnedException (if user has that book already)
     */
    @PostMapping("/attach/{user_id}/{book_id}")
    public void attachBook(@PathVariable Long book_id, @PathVariable Long user_id)
        throws UserNotFoundException, BookNotFoundException, BookAlreadyOwnedException {
        User user = userRepository.findById(user_id)
            .orElseThrow(() -> new UserNotFoundException(user_id.toString(), "id"));
        Book book = bookRepository.findById(book_id)
            .orElseThrow(() -> new BookNotFoundException(book_id.toString(), "id"));
        user.addBook(book);
        userRepository.save(user);
    }
}
