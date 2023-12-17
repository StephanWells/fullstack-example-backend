package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.Currency;
import defs.AcceptedCurrency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CurrencyDAO implements IDAO<Currency, String> {
    private static final Logger logger = LogManager.getLogger(CurrencyDAO.class);

    /**
     * Ensures all currencies defined programmatically in the enum are synced with the database.
     */
    public void synchroniseCurrencies() {
        Database.getInstance().synchronise(AcceptedCurrency.values(), Currency.class);
        logger.info("Synchronised currencies");
    }
}
