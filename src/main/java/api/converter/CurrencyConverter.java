package api.converter;

import api.converter.base.IConverter;
import db.model.Currency;
import defs.dto.CurrencyDTO;
import defs.enums.AcceptedCurrency;
import defs.errors.CurrencyNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Class for converting to and from currency DTOs / database entities.
 */
public class CurrencyConverter implements IConverter<Currency, CurrencyDTO> {
    /**
     * Converts from an accepted currency database entity to a currency DTO.
     *
     * @param currency The currency from the database to convert.
     * @return The currency DTO.
     */
    public CurrencyDTO toDTO(Currency currency) {
        if (currency == null) {
            return null;
        }
        return new CurrencyDTO(currency.getCode(), currency.getSymbol());
    }

    /**
     * Converts from a currency DTO to a database entity.
     *
     * @param currencyDTO The currency DTO to convert.
     * @return A currency database entity.
     */
    public Currency toModel(@NotNull CurrencyDTO currencyDTO) {
        AcceptedCurrency currency =
            Arrays.stream(AcceptedCurrency.values())
                .filter(curr -> curr.code.equals(currencyDTO.getCode()))
                .findFirst()
                .orElse(null);

        if (currency == null) {
            throw new CurrencyNotFoundException("Currency not found", currencyDTO.getCode());
        }
        return currency.toEntity();
    }
}
