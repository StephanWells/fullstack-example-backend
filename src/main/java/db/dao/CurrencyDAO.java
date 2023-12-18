package db.dao;

import db.Database;
import db.dao.base.IDAO;
import db.model.Currency;
import defs.enums.AcceptedCurrency;
import defs.errors.ServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CurrencyDAO implements IDAO<Currency, String> {
    private static final Logger logger = LogManager.getLogger(CurrencyDAO.class);


    /**
     * Finds all currencies in the database.
     *
     * @return A list of currencies.
     * @throws ServerErrorException When any error occurs.
     */
    @Override
    public List<Currency> findAll() {
        try {
            List<Currency> currencies = Database.getInstance().findAllEntities(Currency.class);
            logger.info("Found " + currencies.size() + " currencies");
            return currencies;
        } catch (Exception e) {
            logger.error("Error finding currencies. Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Finds a currency by code.
     *
     * @param code The code primary key.
     * @return A currency, or null if not found.
     * @throws ServerErrorException When any error occurs.
     */
    @Override
    public Currency find(String code) {
        try {
            Currency currency = Database.getInstance().findEntityById(Currency.class, code);
            if (currency != null) {
                logger.info("Found currency with code " + currency.getCode());
            } else {
                logger.info("Currency with code " + code + " not found");
            }
            return currency;
        } catch (Exception e) {
            logger.error("Error finding currency with code " + code + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * Ensures all currencies defined programmatically in the enum are synced with the database.
     */
    public void synchroniseCurrencies() {
        Database.getInstance().synchronise(AcceptedCurrency.values(), Currency.class);
        logger.info("Synchronised currencies");
    }
}
