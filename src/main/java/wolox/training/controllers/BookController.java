package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/author/{author}")
    public Book findByAuthor(@PathVariable String author) throws BookNotFoundException {
        return bookRepository.findFirstByAuthor(author)
            .orElseThrow(() -> new BookNotFoundException(author, "author"));
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) throws BookNotFoundException{
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id.toString(), "id"));
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws BookNotFoundException {
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id.toString(), "id"));
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
