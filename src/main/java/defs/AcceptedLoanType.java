package defs;

import db.model.LoanType;
import defs.base.IDef;
import org.jetbrains.annotations.NotNull;

/**
 * Enum for all the accepted loan types. This serves as the source of truth of loan types used by the system.
 */
public enum AcceptedLoanType implements IDef<LoanType> {
    HOME("Home Loan", "Property"),
    STUDENT("Student Loan", "Course"),
    VEHICLE("Vehicle Loan", "Vehicle");

    public final String loanName;
    public final String loanPurchase;

    AcceptedLoanType(String loanName, String loanPurchase) {
        this.loanName = loanName;
        this.loanPurchase = loanPurchase;
    }

    @Override
    public @NotNull LoanType toEntity() {
        return new LoanType(this);
    }

    @Override
    public void updateEntity(@NotNull LoanType loanType) {
        loanType.setLoanPurchase(this.loanPurchase);
    }
}
