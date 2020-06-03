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
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
        return user;
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

    @PostMapping
    public void attachBook(@RequestBody Book book, @PathVariable Long id)
        throws UserNotFoundException, BookAlreadyOwnedException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
        user.addBook(book);
    }

    @DeleteMapping
    public void detachBook(@RequestBody Book book, @PathVariable Long id)
        throws UserNotFoundException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id.toString(), "id"));
        user.removeBook(book);
    }
}
