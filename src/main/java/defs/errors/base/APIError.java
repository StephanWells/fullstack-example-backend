package defs.errors.base;

/**
 * Base class for an API error.
 */
public class APIError {
    private final String message;
    private final int statusCode;

    public APIError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
