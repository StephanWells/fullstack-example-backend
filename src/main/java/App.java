import db.Database;
import db.dao.*;
import db.model.*;
import defs.AcceptedCurrency;
import defs.AcceptedLoanType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Props;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        Props.initialise();
        Database.initialise();
        LoanApplicantDAO dao = new LoanApplicantDAO();
        LoanApplicant loanApplicant = new LoanApplicant(
            "John",
            "Doe",
            LocalDate.of(1995, 5, 15),
            "+35679389379",
            "johndoe@hotmail.com"
        );
        LoanType loanType = new LoanType(AcceptedLoanType.HOME);
        Currency currency = new Currency(AcceptedCurrency.US_DOLLARS);

        IncomeSource incomeSource1 = new IncomeSource(loanApplicant, "Salary", 5000.0);
        IncomeSource incomeSource2 = new IncomeSource(loanApplicant, "Bonus", 1000.0);

        RecurringExpense recurringExpense1 = new RecurringExpense(loanApplicant, "Rent", 1500.0, currency);
        RecurringExpense recurringExpense2 = new RecurringExpense(loanApplicant, "Utilities", 200.0, currency);

        // Save entities to the database using DAOs
        new LoanApplicantDAO().save(loanApplicant);
        new LoanTypeDAO().save(loanType);
        new CurrencyDAO().save(currency);
        new IncomeSourceDAO().save(incomeSource1);
        new IncomeSourceDAO().save(incomeSource2);
        new RecurringExpenseDAO().save(recurringExpense1);
        new RecurringExpenseDAO().save(recurringExpense2);

        // Set relationships between entities
        List<IncomeSource> incomeSources = Arrays.asList(incomeSource1, incomeSource2);
        List<RecurringExpense> recurringExpenses = Arrays.asList(recurringExpense1, recurringExpense2);

        // Create a new Loan object
        Loan loan = new Loan(loanApplicant, loanType, 200000.0, currency);

        // Set relationships in the LoanApplicant object
        loanApplicant.setIncomeSources(incomeSources);
        loanApplicant.setRecurringExpenses(recurringExpenses);

        // Save the Loan object to the database
        new LoanDAO().save(loan);
    }
}
