package defs.errors.base;

public abstract class APIException extends RuntimeException {
    public final APIError apiError;

    public APIException(String message, APIError apiError) {
        super(message);
        this.apiError = apiError;
    }

    public APIError getApiError() {
        return apiError;
    }
}
