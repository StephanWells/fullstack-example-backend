package db.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * POJO for personal identifiable information of an applicant requesting a loan.
 */
@Entity
@Table(name = "loan_applicants")
public class LoanApplicant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String emailAddress;

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
}
