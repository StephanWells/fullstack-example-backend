package defs.errors;

import defs.errors.base.APIError;
import defs.errors.base.APIException;

public class IllegalIDFieldException extends APIException {
    public IllegalIDFieldException(String message) {
        super(message, new APIError("ID field is not allowed during object creation", 400));
    }
}
