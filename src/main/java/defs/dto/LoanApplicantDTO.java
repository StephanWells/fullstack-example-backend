package defs.dto;

import java.time.LocalDate;
import java.util.List;

public class LoanApplicantDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String emailAddress;
    private List<IncomeSourceDTO> incomeSources;
    private List<RecurringExpenseDTO> recurringExpenses;

    public LoanApplicantDTO() {
    }

    public LoanApplicantDTO(Long id, String firstName, String lastName, LocalDate dateOfBirth,
                            String mobileNumber, String emailAddress, List<IncomeSourceDTO> incomeSources,
                            List<RecurringExpenseDTO> recurringExpenses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.incomeSources = incomeSources;
        this.recurringExpenses = recurringExpenses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<IncomeSourceDTO> getIncomeSources() {
        return incomeSources;
    }

    public void setIncomeSources(List<IncomeSourceDTO> incomeSources) {
        this.incomeSources = incomeSources;
    }

    public List<RecurringExpenseDTO> getRecurringExpenses() {
        return recurringExpenses;
    }

    public void setRecurringExpenses(List<RecurringExpenseDTO> recurringExpenses) {
        this.recurringExpenses = recurringExpenses;
    }
}

