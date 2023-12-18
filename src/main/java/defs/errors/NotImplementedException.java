package defs.errors;

import defs.errors.base.APIError;
import defs.errors.base.APIException;

public class NotImplementedException extends APIException {
    public NotImplementedException(String message) {
        super(message, new APIError("Not implemented: " + message, 501));
    }
}
