package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.LoanApplicant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import util.Validation;

import java.util.List;

public class LoanApplicantDAO implements IDAO<LoanApplicant, Long> {
    private static final Logger logger = LogManager.getLogger(LoanApplicantDAO.class);

    /**
     * Validates fields of loan applicant.
     *
     * @param loanApplicant The loan applicant to validate the fields for.
     * @throws IllegalArgumentException When any of the fields, e.g. email, are invalid.
     */
    @Override
    public void validate(@NotNull LoanApplicant loanApplicant) throws IllegalArgumentException {
        if (!Validation.isValidEmail(loanApplicant.getEmailAddress())) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (!Validation.isValidMobileNumber(loanApplicant.getMobileNumber())) {
            throw new IllegalArgumentException("Invalid mobile number");
        }
        if (!Validation.isValidDateOfBirth(loanApplicant.getDateOfBirth())) {
            throw new IllegalArgumentException("Invalid mobile number");
        }
    }

    /**
     * Saves a loan applicant to the database after validating.
     *
     * @param loanApplicant The loan applicant object to save.
     */
    @Override
    public void save(LoanApplicant loanApplicant) {
        try {
            validate(loanApplicant);
            if (loanApplicant.getId() == null) {
                Database.getInstance().saveEntity(loanApplicant);
                logger.info("Added loan applicant with ID " + loanApplicant.getId() + " to the database");
            } else {
                throw new IllegalStateException("Object identifier already set");
            }

        } catch (Exception e) {
            logger.error("Error saving loan applicant with ID " + loanApplicant.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds a loan applicant by ID.
     *
     * @param id The ID primary key.
     * @return A loan applicant, or null if not found.
     */
    @Override
    public LoanApplicant find(Long id) {
        try {
            LoanApplicant loanApplicant = Database.getInstance().findEntityById(LoanApplicant.class, id);
            if (loanApplicant != null) {
                logger.info("Found loan applicant with ID " + loanApplicant.getId());
            } else {
                logger.info("Loan applicant with ID " + id + " not found");
            }
            return loanApplicant;
        } catch (Exception e) {
            logger.error("Error finding loan applicant with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<LoanApplicant> findAll() {
        try {
            List<LoanApplicant> loanApplicants = Database.getInstance().findAllEntities(LoanApplicant.class);
            logger.info("Found " + loanApplicants.size() + " loan applicants");
            return loanApplicants;
        } catch (Exception e) {
            logger.error("Error finding loan applicants");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates a loan applicant in the database after validating.
     *
     * @param loanApplicant The loan applicant object to update.
     */
    @Override
    public void update(@NotNull LoanApplicant loanApplicant) {
        try {
            validate(loanApplicant);
            Database.getInstance().updateEntity(loanApplicant);
            logger.info("Updated loan applicant with ID " + loanApplicant.getId() + " in the database");
        } catch (Exception e) {
            logger.error("Error updating loan applicant with ID " + loanApplicant.getId() + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a loan applicant by ID.
     *
     * @param id The ID primary key of the loan applicant to delete.
     */
    @Override
    public void delete(Long id) {
        try {
            LoanApplicant loanApplicant = find(id);
            if (loanApplicant != null) {
                Database.getInstance().deleteEntity(loanApplicant);
                logger.info("Deleted loan applicant with ID " + id + " from the database");
            } else {
                logger.info("Loan applicant with ID " + id + " not found. Nothing to delete");
            }
        } catch (Exception e) {
            logger.error("Error deleting loan applicant with ID " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
