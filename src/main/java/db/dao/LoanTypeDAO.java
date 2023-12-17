package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.LoanType;
import defs.AcceptedLoanType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoanTypeDAO implements IDAO<LoanType, String> {
    private static final Logger logger = LogManager.getLogger(LoanTypeDAO.class);

    /**
     * Ensures all loan types defined programmatically in the enum are synced with the database.
     */
    public void synchroniseLoanTypes() {
        Database.getInstance().synchronise(AcceptedLoanType.values(), LoanType.class);
        logger.info("Synchronised loan types");
    }
}
