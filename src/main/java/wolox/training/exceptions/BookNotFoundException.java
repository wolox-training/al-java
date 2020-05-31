package wolox.training.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(final String value, final String attr) {
        super("Book with " + attr + " = " + value + " doesn't found");
    }
}
