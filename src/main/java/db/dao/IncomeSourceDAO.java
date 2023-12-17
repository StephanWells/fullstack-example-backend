package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.IncomeSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.Validation;

public class IncomeSourceDAO implements IDAO<IncomeSource, Long> {
    private static final Logger logger = LogManager.getLogger(IncomeSourceDAO.class);

    /**
     * Validates fields of an income source.
     *
     * @param incomeSource The income source to validate the fields for.
     * @throws IllegalArgumentException When any of the fields are invalid.
     */
    @Override
    public void validate(@NotNull IncomeSource incomeSource) throws IllegalArgumentException {
        if (!Validation.isValidMonetaryValue(incomeSource.getMonthlyIncome())) {
            throw new IllegalArgumentException("Invalid monthly income");
        }
    }

    /**
     * Saves an income source to the database after validating.
     *
     * @param incomeSource The income source object to save.
     */
    @Override
    public void save(IncomeSource incomeSource) {
        try {
            validate(incomeSource);
            if (incomeSource.getId() == null) {
                Database.getInstance().saveEntity(incomeSource);
                logger.info("Added income source with ID " + incomeSource.getId() + " to the database");
            } else {
                throw new IllegalStateException("Object identifier already set");
            }
        } catch (Exception e) {
            logger.error("Error saving income source with ID " + incomeSource.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}