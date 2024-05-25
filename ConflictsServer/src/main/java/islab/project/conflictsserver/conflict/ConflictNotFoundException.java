package islab.project.conflictsserver.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConflictNotFoundException extends RuntimeException {
    public ConflictNotFoundException(String message) {
        super(message);
    }
    public ConflictNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}