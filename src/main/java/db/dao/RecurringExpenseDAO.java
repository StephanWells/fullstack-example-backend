package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.RecurringExpense;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.Validation;

public class RecurringExpenseDAO implements IDAO<RecurringExpense, Long> {
    private static final Logger logger = LogManager.getLogger(RecurringExpenseDAO.class);

    /**
     * Validates fields of a recurring expense.
     *
     * @param recurringExpense The recurring expense to validate the fields for.
     * @throws IllegalArgumentException When any of the fields are invalid.
     */
    @Override
    public void validate(@NotNull RecurringExpense recurringExpense) throws IllegalArgumentException {
        if (!Validation.isValidMonetaryValue(recurringExpense.getMonthlyExpense())) {
            throw new IllegalArgumentException("Invalid monthly expense");
        }
    }

    /**
     * Saves a recurring expense to the database after validating.
     *
     * @param recurringExpense The recurring expense object to save.
     */
    @Override
    public void save(RecurringExpense recurringExpense) {
        try {
            validate(recurringExpense);
            if (recurringExpense.getId() == null) {
                Database.getInstance().saveEntity(recurringExpense);
                logger.info("Added recurring expense with ID " + recurringExpense.getId() + " to the database");
            } else {
                throw new IllegalStateException("Object identifier already set");
            }
        } catch (Exception e) {
            logger.error("Error saving recurring expense with ID " + recurringExpense.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
