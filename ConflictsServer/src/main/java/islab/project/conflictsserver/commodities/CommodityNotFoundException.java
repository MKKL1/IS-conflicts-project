package islab.project.conflictsserver.commodities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommodityNotFoundException extends RuntimeException {
    public CommodityNotFoundException(String message) {
        super(message);
    }
    public CommodityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
