package db.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * POJO for a loan.
 */
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @Column(name = "loan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private LoanApplicant loanApplicant;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @Column(name = "value_of_purchase")
    private double valueOfPurchase;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    // Default constructor
    public Loan() {
    }

    // Constructor when ID is not known (for record creation)
    public Loan(LoanApplicant loanApplicant, LoanType loanType, double valueOfPurchase, Currency currency) {
        this.loanApplicant = loanApplicant;
        this.loanType = loanType;
        this.valueOfPurchase = valueOfPurchase;
        this.currency = currency;
    }

    // Constructor when ID is known (for record updating)
    public Loan(Long id, LoanApplicant loanApplicant, LoanType loanType, double valueOfPurchase, Currency currency) {
        this.id = id;
        this.loanApplicant = loanApplicant;
        this.loanType = loanType;
        this.valueOfPurchase = valueOfPurchase;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoanApplicant getLoanApplicant() {
        return loanApplicant;
    }

    public void setLoanApplicant(LoanApplicant loanApplicant) {
        this.loanApplicant = loanApplicant;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public double getValueOfPurchase() {
        return valueOfPurchase;
    }

    public void setValueOfPurchase(double valueOfPurchase) {
        this.valueOfPurchase = valueOfPurchase;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id.equals(loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
