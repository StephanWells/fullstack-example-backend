package defs.dto;

import java.util.Objects;

public class RecurringExpenseDTO {
    private Long id;
    private Long applicantId;
    private String expenseType;
    private double monthlyExpense;
    private String currency;

    public RecurringExpenseDTO() {
    }

    public RecurringExpenseDTO(Long id, Long applicantId, String expenseType, double monthlyExpense,
                               String currency) {
        this.id = id;
        this.applicantId = applicantId;
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

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public double getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringExpenseDTO that = (RecurringExpenseDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
