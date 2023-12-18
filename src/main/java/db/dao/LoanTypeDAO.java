package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.LoanType;
import defs.enums.AcceptedLoanType;
import defs.errors.ServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LoanTypeDAO implements IDAO<LoanType, String> {
    private static final Logger logger = LogManager.getLogger(LoanTypeDAO.class);

    /**
     * Finds all loan types in the database.
     *
     * @return A list of loan types.
     * @throws ServerErrorException When any error occurs.
     */
    @Override
    public List<LoanType> findAll() {
        try {
            List<LoanType> loanTypes = Database.getInstance().findAllEntities(LoanType.class);
            logger.info("Found " + loanTypes.size() + " loan types");
            return loanTypes;
        } catch (Exception e) {
            logger.error("Error finding loan types. Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Finds a loan type by loan name.
     *
     * @param loanName The loan name primary key.
     * @return A loan type, or null if not found.
     * @throws ServerErrorException When any error occurs.
     */
    public LoanType find(String loanName) {
        try {
            LoanType loanType = Database.getInstance().findEntityById(LoanType.class, loanName);
            if (loanType != null) {
                logger.info("Found loan type with code " + loanType.getLoanName());
            } else {
                logger.info("Loan type with name " + loanName + " not found");
            }
            return loanType;
        } catch (Exception e) {
            logger.error("Error finding loan type with name " + loanName + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Ensures all loan types defined programmatically in the enum are synced with the database.
     */
    public void synchroniseLoanTypes() {
        Database.getInstance().synchronise(AcceptedLoanType.values(), LoanType.class);
        logger.info("Synchronised loan types");
    }
}
