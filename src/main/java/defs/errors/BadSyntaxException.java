package defs.errors;

import defs.errors.base.APIError;
import defs.errors.base.APIException;

public class BadSyntaxException extends APIException {
    public BadSyntaxException(String message) {
        super(message, new APIError("Malformed request: " + message, 400));
    }
}
