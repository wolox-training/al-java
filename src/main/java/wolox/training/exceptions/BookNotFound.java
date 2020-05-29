package wolox.training.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(final Long id) {
        super("Book with id: " + id + " doesn't found");
    }
}
