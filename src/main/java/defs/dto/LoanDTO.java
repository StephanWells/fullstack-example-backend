package defs.dto;

import java.util.Objects;

public class LoanDTO {
    private Long id;
    private Long applicantId;
    private String loanType;
    private double valueOfPurchase;
    private String currency;

    public LoanDTO() {
    }

    public LoanDTO(Long id, Long applicantId, String loanType, double valueOfPurchase, String currency) {
        this.id = id;
        this.applicantId = applicantId;
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

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getValueOfPurchase() {
        return valueOfPurchase;
    }

    public void setValueOfPurchase(double valueOfPurchase) {
        this.valueOfPurchase = valueOfPurchase;
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
        LoanDTO loanDTO = (LoanDTO) o;
        return id.equals(loanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


