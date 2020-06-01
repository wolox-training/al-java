package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User already has this book")
public class BookAlreadyOwnedException extends Exception {
    public BookAlreadyOwnedException() {
        super("User already has this book");
    }
}
