package db.model;

import jakarta.persistence.*;

/**
 * POJO for an income source tied to a loan.
 */
@Entity
@Table(name = "income_sources")
public class IncomeSource {
    @Id
    @Column(name = "income_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private LoanApplicant loanApplicant;

    @Column(name = "income_type")
    private String incomeType;

    @Column(name = "monthly_income")
    private double monthlyIncome;

    // Default constructor
    public IncomeSource() {
    }

    // Constructor when ID is not known (for record creation)
    public IncomeSource(LoanApplicant loanApplicant, String incomeType, double monthlyIncome) {
        this.loanApplicant = loanApplicant;
        this.incomeType = incomeType;
        this.monthlyIncome = monthlyIncome;
    }

    // Constructor when ID is known (for record updating)
    public IncomeSource(Long id, LoanApplicant loanApplicant, String incomeType, double monthlyIncome) {
        this.id = id;
        this.loanApplicant = loanApplicant;
        this.incomeType = incomeType;
        this.monthlyIncome = monthlyIncome;
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

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
}
