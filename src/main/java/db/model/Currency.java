package db.model;

import defs.enums.AcceptedCurrency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * POJO for a currency. This table is automatically populated by the AcceptedCurrency enum values.
 */
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;

    public Currency() {
    }

    public Currency(AcceptedCurrency currency) {
        this.code = currency.code;
        this.symbol = currency.symbol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return code.equals(currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}