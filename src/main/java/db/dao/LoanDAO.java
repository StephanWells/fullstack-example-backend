package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.Loan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.Validation;

import java.util.List;

public class LoanDAO implements IDAO<Loan, Long> {
    private static final Logger logger = LogManager.getLogger(LoanDAO.class);

    /**
     * Validates fields of a loan.
     *
     * @param loan The loan to validate the fields for.
     * @throws IllegalArgumentException When any of the fields are invalid.
     */
    @Override
    public void validate(@NotNull Loan loan) throws IllegalArgumentException {
        if (!Validation.isValidMonetaryValue(loan.getValueOfPurchase())) {
            throw new IllegalArgumentException("Invalid value of purchase");
        }
    }

    /**
     * Saves a loan to the database after validating.
     *
     * @param loan The loan object to save.
     */
    @Override
    public void save(Loan loan) {
        try {
            validate(loan);
            if (loan.getId() == null) {
                Database.getInstance().saveEntity(loan);
                logger.info("Added loan with ID " + loan.getId() + " to the database");
            } else {
                throw new IllegalStateException("Object identifier already set");
            }
        } catch (Exception e) {
            logger.error("Error saving loan with ID " + loan.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds a loan by ID.
     *
     * @param id The ID primary key.
     * @return A loan, or null if not found.
     */
    @Override
    public Loan find(Long id) {
        try {
            Loan loan = Database.getInstance().findEntityById(Loan.class, id);
            if (loan != null) {
                logger.info("Found loan with ID " + loan.getId());
            } else {
                logger.info("Loan with ID " + id + " not found");
            }
            return loan;
        } catch (Exception e) {
            logger.error("Error finding loan with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Finds all loans in the database.
     *
     * @return A list of loans.
     */
    @Override
    public List<Loan> findAll() {
        try {
            List<Loan> loans = Database.getInstance().findAllEntities(Loan.class);
            logger.info("Found " + loans.size() + " loans");
            return loans;
        } catch (Exception e) {
            logger.error("Error finding loans");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates a loan in the database after validating.
     *
     * @param loan The loan object to update.
     */
    @Override
    public void update(@NotNull Loan loan) {
        try {
            validate(loan);
            Database.getInstance().updateEntity(loan);
            logger.info("Updated loan with ID " + loan.getId() + " in the database");
        } catch (Exception e) {
            logger.error("Error updating loan with ID " + loan.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a loan by ID.
     *
     * @param id The ID primary key of the loan to delete.
     */
    @Override
    public void delete(Long id) {
        try {
            Loan loan = find(id);
            if (loan != null) {
                Database.getInstance().deleteEntity(loan);
                logger.info("Deleted loan with ID " + id + " from the database");
            } else {
                logger.info("Loan with ID " + id + " not found. Nothing to delete");
            }
        } catch (Exception e) {
            logger.error("Error deleting loan with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
