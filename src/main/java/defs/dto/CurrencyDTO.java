package defs.dto;

import java.util.Objects;

public class CurrencyDTO {
    private String code;
    private String symbol;

    public CurrencyDTO() {
    }

    public CurrencyDTO(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
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
        CurrencyDTO that = (CurrencyDTO) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
