package db.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POJO for personal identifiable information of an applicant requesting a loan.
 */
@Entity
@Table(name = "loan_applicants")
public class LoanApplicant implements Serializable {
    @Id
    @Column(name = "applicant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicant")
    @Column(name = "income_sources")
    private List<IncomeSource> incomeSources;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicant")
    @Column(name = "recurring_expenses")
    private List<RecurringExpense> recurringExpenses;

    // Default constructor
    public LoanApplicant() {
    }

    // Constructor when ID is not known (for record creation)
    public LoanApplicant(String firstName, String lastName, LocalDate dateOfBirth, String mobileNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.incomeSources = new ArrayList<>();
        this.recurringExpenses = new ArrayList<>();
    }

    // Constructor when ID is known (for record updating)
    public LoanApplicant(Long id, String firstName, String lastName, LocalDate dateOfBirth, String mobileNumber,
                         String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.incomeSources = new ArrayList<>();
        this.recurringExpenses = new ArrayList<>();
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

    public List<IncomeSource> getIncomeSources() {
        return incomeSources;
    }

    public void setIncomeSources(List<IncomeSource> incomeSources) {
        this.incomeSources = incomeSources;
    }

    public List<RecurringExpense> getRecurringExpenses() {
        return recurringExpenses;
    }

    public void setRecurringExpenses(List<RecurringExpense> recurringExpenses) {
        this.recurringExpenses = recurringExpenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApplicant loanApplicant = (LoanApplicant) o;
        return id.equals(loanApplicant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
