package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.RecurringExpense;
import defs.errors.BadSyntaxException;
import defs.errors.IllegalIDFieldException;
import defs.errors.ServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.function.Validation;

public class RecurringExpenseDAO implements IDAO<RecurringExpense, Long> {
    private static final Logger logger = LogManager.getLogger(RecurringExpenseDAO.class);

    /**
     * Validates fields of a recurring expense.
     *
     * @param recurringExpense The recurring expense to validate the fields for.
     * @throws BadSyntaxException When any of the fields are invalid.
     */
    @Override
    public void validate(@NotNull RecurringExpense recurringExpense) throws IllegalArgumentException {
        if (!Validation.isValidMonetaryValue(recurringExpense.getMonthlyExpense())) {
            throw new BadSyntaxException("Invalid monthly expense");
        }
    }

    /**
     * Saves a recurring expense to the database after validating.
     *
     * @param recurringExpense The recurring expense object to save.
     * @throws IllegalIDFieldException When the recurring expense ID is already set.
     * @throws ServerErrorException    When any other error occurs.
     */
    @Override
    public void save(RecurringExpense recurringExpense) {
        try {
            validate(recurringExpense);
            if (recurringExpense.getId() == null) {
                Database.getInstance().saveEntity(recurringExpense);
                logger.info("Added recurring expense with ID " + recurringExpense.getId() + " to the database");
            } else {
                throw new IllegalIDFieldException("Object identifier already set");
            }
        } catch (IllegalIDFieldException e) {
            throw (e);
        } catch (Exception e) {
            logger.error("Error saving recurring expense with ID " + recurringExpense.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }
}
