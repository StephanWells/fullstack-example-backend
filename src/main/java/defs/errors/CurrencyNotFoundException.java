package defs.errors;

import defs.enums.AcceptedCurrency;
import defs.errors.base.APIError;
import defs.errors.base.APIException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CurrencyNotFoundException extends APIException {
    public CurrencyNotFoundException(String message, String currencyString) {
        super(message, new APIError("Currency " + currencyString + " not found. Currencies must be one of the " +
            "following: " + Arrays.stream(AcceptedCurrency.values()).map(curr -> curr.code).collect(Collectors.joining(", ")), 404));
    }
}
