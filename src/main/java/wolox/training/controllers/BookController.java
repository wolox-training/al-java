package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/author/{author}")
    public Optional<Book> findByAuthor(@PathVariable String author){
        return bookRepository.findFirstByAuthor(author).orElseThrow(BookNotFoundException::new);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book){
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PostMapping
    public void create(@RequestBody Book book){
        bookRepository.save(book);
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false)
        String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
