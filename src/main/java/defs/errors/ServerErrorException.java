package defs.errors;

import defs.errors.base.APIError;
import defs.errors.base.APIException;

public class ServerErrorException extends APIException {
    public ServerErrorException(String message) {
        super(message, new APIError("Server error: " + message, 500));
    }
}
