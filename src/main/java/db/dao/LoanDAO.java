package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.Loan;
import defs.errors.BadSyntaxException;
import defs.errors.IllegalIDFieldException;
import defs.errors.NotFoundException;
import defs.errors.ServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.function.Validation;

import java.util.List;

public class LoanDAO implements IDAO<Loan, Long> {
    private static final Logger logger = LogManager.getLogger(LoanDAO.class);

    /**
     * Validates fields of a loan.
     *
     * @param loan The loan to validate the fields for.
     * @throws BadSyntaxException When any of the fields, e.g. value of purchase, are invalid.
     */
    @Override
    public void validate(@NotNull Loan loan) throws IllegalArgumentException {
        if (!Validation.isValidMonetaryValue(loan.getValueOfPurchase())) {
            throw new BadSyntaxException("Invalid value of purchase");
        }
    }

    /**
     * Saves a loan to the database after validating.
     *
     * @param loan The loan object to save.
     * @throws IllegalIDFieldException When the loan ID is already set.
     * @throws ServerErrorException    When any other error occurs.
     */
    @Override
    public void save(Loan loan) {
        try {
            validate(loan);
            if (loan.getId() == null) {
                Database.getInstance().saveEntity(loan);
                logger.info("Added loan with ID " + loan.getId() + " to the database");
            } else {
                throw new IllegalIDFieldException("Object identifier already set");
            }
        } catch (IllegalIDFieldException e) {
            throw (e);
        } catch (Exception e) {
            logger.error("Error saving loan with ID " + loan.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Finds a loan by ID.
     *
     * @param id The ID primary key.
     * @return A loan, or null if not found.
     * @throws NotFoundException    When the loan object is not found.
     * @throws ServerErrorException When any other error occurs.
     */
    @Override
    public Loan find(Long id) {
        try {
            Loan loan = Database.getInstance().findEntityById(Loan.class, id);
            if (loan != null) {
                logger.info("Found loan with ID " + loan.getId());
            } else {
                throw new NotFoundException("Not found", id);
            }
            return loan;
        } catch (NotFoundException e) {
            logger.info("Loan with ID " + id + " not found");
            throw (e);
        } catch (Exception e) {
            logger.error("Error finding loan with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Finds all loans in the database.
     *
     * @return A list of loans.
     * @throws ServerErrorException When any error occurs.
     */
    @Override
    public List<Loan> findAll() {
        try {
            List<Loan> loans = Database.getInstance().findAllEntities(Loan.class);
            logger.info("Found " + loans.size() + " loans");
            return loans;
        } catch (Exception e) {
            logger.error("Error finding loans. Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Updates a loan in the database after validating.
     *
     * @param loan The loan object to update.
     * @throws NotFoundException    When the loan object is not found.
     * @throws ServerErrorException When any other error occurs.
     */
    @Override
    public void update(@NotNull Loan loan) {
        try {
            validate(loan);
            Loan foundLoan = find(loan.getId());
            if (foundLoan != null) {
                Database.getInstance().updateEntity(loan);
                logger.info("Updated loan with ID " + loan.getId() + " in the database");
            } else {
                throw new NotFoundException("Not found", loan.getId());
            }
        } catch (NotFoundException e) {
            logger.info("Loan with ID " + loan.getId() + " not found. Nothing to update");
        } catch (Exception e) {
            logger.error("Error updating loan with ID " + loan.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Deletes a loan by ID.
     *
     * @param id The ID primary key of the loan to delete.
     * @throws NotFoundException    When the loan object is not found.
     * @throws ServerErrorException When any other error occurs.
     */
    @Override
    public void delete(Long id) {
        try {
            Loan loan = find(id);
            if (loan != null) {
                Database.getInstance().deleteEntity(loan);
                logger.info("Deleted loan with ID " + id + " from the database");
            } else {
                throw new NotFoundException("Not found", id);
            }
        } catch (NotFoundException e) {
            logger.info("Loan with ID " + id + " not found. Nothing to delete");
            throw (e);
        } catch (Exception e) {
            logger.error("Error deleting loan with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }
}
