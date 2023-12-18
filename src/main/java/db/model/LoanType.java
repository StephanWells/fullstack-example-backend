package db.model;

import defs.enums.AcceptedLoanType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * POJO for a loan type. This table is automatically populated by the AcceptedLoanType enum values.
 */
@Entity
@Table(name = "loan_types")
public class LoanType {
    @Id
    @Column(name = "loan_name")
    private String loanName;

    @Column(name = "loan_purchase")
    private String loanPurchase;

    public LoanType() {
    }

    public LoanType(@NotNull AcceptedLoanType loanType) {
        this.loanName = loanType.loanName;
        this.loanPurchase = loanType.loanPurchase;
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
        LoanType loanType = (LoanType) o;
        return loanName.equals(loanType.loanName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanName);
    }
}
