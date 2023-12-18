package defs.errors;

import defs.errors.base.APIError;
import defs.errors.base.APIException;

public class NotFoundException extends APIException {
    public <ID> NotFoundException(String message, ID primaryKey) {
        super(message, new APIError("Entity with ID " + primaryKey + " not found", 404));
    }
}
