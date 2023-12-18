package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.IncomeSource;
import defs.errors.BadSyntaxException;
import defs.errors.IllegalIDFieldException;
import defs.errors.ServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.function.Validation;

public class IncomeSourceDAO implements IDAO<IncomeSource, Long> {
    private static final Logger logger = LogManager.getLogger(IncomeSourceDAO.class);

    /**
     * Validates fields of an income source.
     *
     * @param incomeSource The income source to validate the fields for.
     * @throws BadSyntaxException When any of the fields are invalid.
     */
    @Override
    public void validate(@NotNull IncomeSource incomeSource) throws IllegalArgumentException {
        if (!Validation.isValidMonetaryValue(incomeSource.getMonthlyIncome())) {
            throw new BadSyntaxException("Invalid monthly income");
        }
    }

    /**
     * Saves an income source to the database after validating.
     *
     * @param incomeSource The income source object to save.
     * @throws IllegalIDFieldException When the recurring expense ID is already set.
     * @throws ServerErrorException    When any other error occurs.
     */
    @Override
    public void save(IncomeSource incomeSource) {
        try {
            validate(incomeSource);
            if (incomeSource.getId() == null) {
                Database.getInstance().saveEntity(incomeSource);
                logger.info("Added income source with ID " + incomeSource.getId() + " to the database");
            } else {
                throw new IllegalIDFieldException("Object identifier already set");
            }
        } catch (IllegalIDFieldException e) {
            throw (e);
        } catch (Exception e) {
            logger.error("Error saving income source with ID " + incomeSource.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }
}