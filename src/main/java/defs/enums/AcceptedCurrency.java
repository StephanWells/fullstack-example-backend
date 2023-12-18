package defs.enums;

import db.model.Currency;
import defs.enums.base.IDef;
import org.jetbrains.annotations.NotNull;

/**
 * Enum for all the accepted currencies. This serves as the source of truth of currencies used by the system.
 */
public enum AcceptedCurrency implements IDef<Currency> {
    EURO("EUR", "€"),
    US_DOLLARS("USD", "$"),
    UK_POUNDS("GBP", "£");

    public final String code;
    public final String symbol;

    AcceptedCurrency(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    @Override
    public @NotNull Currency toEntity() {
        return new Currency(this);
    }

    @Override
    public void updateEntity(@NotNull Currency currency) {
        currency.setSymbol(this.symbol);
    }
}
