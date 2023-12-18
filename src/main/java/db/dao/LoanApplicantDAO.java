package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.LoanApplicant;
import defs.errors.BadSyntaxException;
import defs.errors.IllegalIDFieldException;
import defs.errors.NotFoundException;
import defs.errors.ServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.function.Validation;

import java.util.List;

public class LoanApplicantDAO implements IDAO<LoanApplicant, Long> {
    private static final Logger logger = LogManager.getLogger(LoanApplicantDAO.class);

    /**
     * Validates fields of loan applicant.
     *
     * @param loanApplicant The loan applicant to validate the fields for.
     * @throws BadSyntaxException When any of the fields, e.g. email, are invalid.
     */
    @Override
    public void validate(@NotNull LoanApplicant loanApplicant) throws IllegalArgumentException {
        if (!Validation.isValidEmail(loanApplicant.getEmailAddress())) {
            throw new BadSyntaxException("Invalid email");
        }
        if (!Validation.isValidMobileNumber(loanApplicant.getMobileNumber())) {
            throw new BadSyntaxException("Invalid mobile number");
        }
        if (!Validation.isValidDateOfBirth(loanApplicant.getDateOfBirth())) {
            throw new BadSyntaxException("Invalid mobile number");
        }
    }

    /**
     * Saves a loan applicant to the database after validating.
     *
     * @param loanApplicant The loan applicant object to save.
     * @throws IllegalIDFieldException When the loan applicant ID is already set.
     * @throws ServerErrorException    When any other error occurs.
     */
    @Override
    public void save(LoanApplicant loanApplicant) {
        try {
            validate(loanApplicant);
            if (loanApplicant.getId() == null) {
                Database.getInstance().saveEntity(loanApplicant);
                logger.info("Added loan applicant with ID " + loanApplicant.getId() + " to the database");
            } else {
                throw new IllegalIDFieldException("Object identifier already set");
            }

        } catch (IllegalIDFieldException e) {
            throw (e);
        } catch (Exception e) {
            logger.error("Error saving loan applicant with ID " + loanApplicant.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Finds a loan applicant by ID.
     *
     * @param id The ID primary key.
     * @return A loan applicant, or null if not found.
     * @throws NotFoundException    When the loan applicant object is not found.
     * @throws ServerErrorException When any other error occurs.
     */
    @Override
    public LoanApplicant find(Long id) {
        try {
            LoanApplicant loanApplicant = Database.getInstance().findEntityById(LoanApplicant.class, id);
            if (loanApplicant != null) {
                logger.info("Found loan applicant with ID " + loanApplicant.getId());
            } else {
                throw new NotFoundException("Not found", id);
            }
            return loanApplicant;
        } catch (NotFoundException e) {
            logger.info("Loan applicant with ID " + id + " not found");
            throw (e);
        } catch (Exception e) {
            logger.error("Error finding loan applicant with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Finds all loan applicants in the database.
     *
     * @return A list of loan applicants.
     * @throws ServerErrorException When any error occurs.
     */
    @Override
    public List<LoanApplicant> findAll() {
        try {
            List<LoanApplicant> loanApplicants = Database.getInstance().findAllEntities(LoanApplicant.class);
            logger.info("Found " + loanApplicants.size() + " loan applicants");
            return loanApplicants;
        } catch (Exception e) {
            logger.error("Error finding loan applicants");
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Updates a loan applicant in the database after validating.
     *
     * @param loanApplicant The loan applicant object to update.
     * @throws NotFoundException    When the loan applicant object is not found.
     * @throws ServerErrorException When any other error occurs.
     */
    @Override
    public void update(@NotNull LoanApplicant loanApplicant) {
        try {
            validate(loanApplicant);
            LoanApplicant foundLoanApplicant = find(loanApplicant.getId());
            if (foundLoanApplicant != null) {
                Database.getInstance().updateEntity(loanApplicant);
                logger.info("Updated loan applicant with ID " + loanApplicant.getId() + " in the database");
            } else {
                throw new NotFoundException("Not found", loanApplicant.getId());
            }

        } catch (NotFoundException e) {
            logger.info("Loan applicant with ID " + loanApplicant.getId() + " not found. Nothing to update");
            throw (e);
        } catch (Exception e) {
            logger.error("Error updating loan applicant with ID " + loanApplicant.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Deletes a loan applicant by ID.
     *
     * @param id The ID primary key of the loan applicant to delete.
     * @throws NotFoundException    When the loan applicant object is not found.
     * @throws ServerErrorException When any other error occurs.
     */
    @Override
    public void delete(Long id) {
        try {
            LoanApplicant loanApplicant = find(id);
            if (loanApplicant != null) {
                Database.getInstance().deleteEntity(loanApplicant);
                logger.info("Deleted loan applicant with ID " + id + " from the database");
            } else {
                throw new NotFoundException("Not found", id);
            }
        } catch (NotFoundException e) {
            logger.info("Loan applicant with ID " + id + " not found. Nothing to delete");
            throw (e);
        } catch (Exception e) {
            logger.error("Error deleting loan applicant with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }
}
