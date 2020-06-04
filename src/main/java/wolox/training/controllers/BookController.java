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

    /**
     * Returns the first Book that is found filtered by its author
     * @param author (Method will search a book by this field)
     * @return {@link Book} (The first book that it finds)
     * @throws BookNotFoundException (If we can't find a book by this author)
     */
    @GetMapping("/author/{author}")
    public Book findByAuthor(@PathVariable String author) throws BookNotFoundException {
        return bookRepository.findFirstByAuthor(author)
            .orElseThrow(() -> new BookNotFoundException(author, "author"));
    }

    /**
     * Updates the given Book
     * @param id (Id of the Book)
     * @param book (Params for book update)
     * @return {@link Book} Updated book
     * @throws BookNotFoundException (If we can't find a book by this id)
     */
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) throws BookNotFoundException{
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id.toString(), "id"));
        return bookRepository.save(book);
    }

    /**
     * Deletes the given Book
     * @param id (Id of the Book)
     * @throws BookNotFoundException (If we can't find a book by this id)
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws BookNotFoundException {
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id.toString(), "id"));
        bookRepository.deleteById(id);
    }

    /**
     * Creates a new Book
     * @param book (The Book will be created)
     * @return {@link Book} Created book
     */
    @PostMapping
    public Book create(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false)
        String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
