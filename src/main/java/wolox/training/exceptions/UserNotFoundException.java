package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends Exception{
    public UserNotFoundException(final String value, final String attr) {
        super("User with " + attr + " = " + value + " doesn't found");
    }
}
