package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
@Api
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
    @ApiOperation(value = "Giving an Id and a Book, it updates that book", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfuly updated book"),
        @ApiResponse(code = 404, message = "Book Not Found"),
        @ApiResponse(code = 401, message = "You are not authorized to access this resource"),
        @ApiResponse(code = 404, message = "The resource you are trying to access was not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public Book updateBook(
        @ApiParam(value = "id to find book") @PathVariable Long id,
        @ApiParam(value = "body of Book") @RequestBody Book book)
        throws BookNotFoundException{
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
    @ResponseStatus(HttpStatus.CREATED)
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
