package db.model;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * POJO for an recurring expense tied to a loan.
 */
@Entity
@Table(name = "recurring_expenses")
public class RecurringExpense implements Serializable {
    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicant_id")
    private LoanApplicant loanApplicant;

    @Column(name = "income_type")
    private String expenseType;

    @Column(name = "monthly_expense")
    private double monthlyExpense;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency")
    private Currency currency;

    // Default constructor
    public RecurringExpense() {
    }

    // Constructor when ID is not known (for record creation)
    public RecurringExpense(LoanApplicant loanApplicant, String expenseType, double monthlyExpense, Currency currency) {
        this.loanApplicant = loanApplicant;
        this.expenseType = expenseType;
        this.monthlyExpense = monthlyExpense;
        this.currency = currency;
    }

    // Constructor when ID is known (for record updating)
    public RecurringExpense(Long id, LoanApplicant loanApplicant, String expenseType, double monthlyExpense, Currency currency) {
        this.id = id;
        this.loanApplicant = loanApplicant;
        this.expenseType = expenseType;
        this.monthlyExpense = monthlyExpense;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}
