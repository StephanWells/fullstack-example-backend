package defs.dto;

import java.util.Objects;

public class IncomeSourceDTO {
    private Long id;
    private Long applicantId;
    private String incomeType;
    private double monthlyIncome;
    private String currency;

    public IncomeSourceDTO() {
    }

    public IncomeSourceDTO(Long id, Long applicantId, String incomeType, double monthlyIncome,
                           String currency) {
        this.id = id;
        this.applicantId = applicantId;
        this.incomeType = incomeType;
        this.monthlyIncome = monthlyIncome;
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
        IncomeSourceDTO that = (IncomeSourceDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
