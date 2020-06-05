package wolox.training.controllers;

import java.util.ArrayList;
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

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException{
        userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws UserNotFoundException {
        userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
        userRepository.deleteById(id);
    }

    @PostMapping
    public void create(@RequestBody User user){
        userRepository.save(user);
    }

    @DeleteMapping("/attach/{user_id}/{book_id}")
    public void detachBook(@PathVariable Long book_id, @PathVariable Long user_id)
        throws UserNotFoundException, BookNotFoundException, BookAlreadyOwnedException {
        User user = userRepository.findById(user_id)
            .orElseThrow(() -> new UserNotFoundException(user_id.toString(), "id"));
        Book book = bookRepository.findById(book_id)
            .orElseThrow(() -> new BookNotFoundException(book_id.toString(), "id"));
        user.removeBook(book);
    }

    @PostMapping("/attach/{user_id}/{book_id}")
    public void attachBook(@PathVariable Long book_id, @PathVariable Long user_id)
        throws UserNotFoundException, BookNotFoundException, BookAlreadyOwnedException {
        User user = userRepository.findById(user_id)
            .orElseThrow(() -> new UserNotFoundException(user_id.toString(), "id"));
        Book book = bookRepository.findById(book_id)
            .orElseThrow(() -> new BookNotFoundException(book_id.toString(), "id"));
        user.addBook(book);
    }
}
