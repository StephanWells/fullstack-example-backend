package defs.dto;

import java.util.Objects;

public class LoanTypeDTO {
    private String loanName;
    private String loanPurchase;

    public LoanTypeDTO() {
    }

    public LoanTypeDTO(String loanName, String loanPurchase) {
        this.loanName = loanName;
        this.loanPurchase = loanPurchase;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getLoanPurchase() {
        return loanPurchase;
    }

    public void setLoanPurchase(String loanPurchase) {
        this.loanPurchase = loanPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanTypeDTO that = (LoanTypeDTO) o;
        return loanName.equals(that.loanName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanName);
    }
}
