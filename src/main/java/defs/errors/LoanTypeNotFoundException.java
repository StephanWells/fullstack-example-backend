package defs.errors;

import defs.enums.AcceptedLoanType;
import defs.errors.base.APIError;
import defs.errors.base.APIException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LoanTypeNotFoundException extends APIException {
    public LoanTypeNotFoundException(String message, String loanTypeString) {
        super(message, new APIError("Loan type " + loanTypeString + " not found. Loan types must be one of the " +
            "following: " + Arrays.stream(AcceptedLoanType.values()).map(lType -> lType.loanName).collect(Collectors.joining(
            ", ")),
            404));
    }
}
