package db;

import db.dao.CurrencyDAO;
import db.dao.LoanTypeDAO;
import defs.enums.base.IDef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import util.config.Props;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static util.function.Util.getClasses;

/**
 * A singleton class to provide an instance of the database to perform operations on.
 */
public class Database {
    private SessionFactory sessionFactory;
    private final Connection connection;
    private static Database instance;

    private static final String HIBERNATE_CONFIG_FILE = "hibernate.cfg.xml";
    private static final Logger logger = LogManager.getLogger(Database.class);

    private Database(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * initialise the database. This method will import the properties, create the database, store the connection,
     * initialise the Hibernate ORM, store the session factory for the database, and initialise/update definitions.
     */
    public static void initialise() {
        initialiseDb();
        Database.instance.initialiseHibernate();
        Database.instance.synchroniseDefinitions();
    }

    /**
     * initialises the database instance with imported credentials.
     *
     * @throws ExceptionInInitializerError When the database fails to be initialised.
     */
    private static void initialiseDb() {
        final String dbUrl = Props.getDbHost() + ":" + Props.getDbPort();
        final String dbUser = Props.getDbUser();
        final String dbPassword = Props.getDbPassword();
        final String dbName = Props.getDbName();

        // initialise MySQL database
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS " + dbName;
            try (PreparedStatement statement = connection.prepareStatement(sqlCreateDB)) {
                statement.executeUpdate();
            }
            logger.info("Database " + dbName + " initialised");
            Database.instance = new Database(connection);
        } catch (SQLException e) {
            logger.error("Error initialising database");
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * initialises the Hibernate instance and related credentials. All classes in the "db.model" package are added as
     * Hibernate class mappings.
     *
     * @throws ExceptionInInitializerError When Hibernate fails to be initialised.
     */
    private void initialiseHibernate() {
        try {
            // Inject properties into Hibernate config
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure(HIBERNATE_CONFIG_FILE)
                .applySetting("hibernate.connection.url",
                    Props.getDbHost() + ":" + Props.getDbPort() + "/" + Props.getDbName())
                .applySetting("hibernate.connection.username", Props.getDbUser())
                .applySetting("hibernate.connection.password", Props.getDbPassword())
                .build();

            // Add class mappings to Hibernate config
            MetadataSources metadataSources = new MetadataSources(standardRegistry);
            for (Class<?> entityClass : getClasses("db.model")) {
                metadataSources.addAnnotatedClass(entityClass);
            }
            Metadata metadata = metadataSources.buildMetadata();

            this.sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            logger.error("Error initializing Hibernate ORM");
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Synchronises all enum definitions (e.g. currencies, loan types) with the database.
     */
    private void synchroniseDefinitions() {
        CurrencyDAO currencyDao = new CurrencyDAO();
        currencyDao.synchroniseCurrencies();
        LoanTypeDAO loanTypeDAO = new LoanTypeDAO();
        loanTypeDAO.synchroniseLoanTypes();
    }

    /**
     * Returns the instance of this database singleton class.
     *
     * @return The database instance.
     * @throws IllegalStateException When the database has not been initialised yet.
     */
    public static Database getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Database not initialised yet");
        }
        return instance;
    }

    /**
     * Opens a session using the Hibernate session factory.
     *
     * @return A Hibernate session.
     * @throws IllegalStateException When the session factory is closed.
     */
    public Session openSession() {
        if (sessionFactory.isClosed()) {
            throw new IllegalStateException("Session factory is closed");
        }
        return sessionFactory.openSession();
    }

    /**
     * A private interface to allow passing a lambda function to wrap inside a database session.
     *
     * @param <Response> The optional response expected from the lambda function.
     */
    @FunctionalInterface
    public interface IDatabaseAction<Response> {
        Optional<Response> performAction(Session session);
    }

    /**
     * A generic method to wrap an action inside a database session.
     *
     * @param databaseAction The action to wrap inside a database session.
     * @param <Response>     The response expected from the action.
     * @return An optional response (if the action in the transaction is supposed to pass a response).
     */
    public <Response> Optional<Response> performDatabaseAction(IDatabaseAction<Response> databaseAction,
                                                               boolean transactional) {
        try (Session session = openSession()) {
            logger.debug("Begin " + (transactional ? "" : "non-") + "transactional database session");
            if (transactional) {
                Transaction transaction = session.beginTransaction();
                Optional<Response> response = databaseAction.performAction(session);
                transaction.commit();
                logger.debug("Completed transactional database session");
                return response;
            } else {
                Optional<Response> response = databaseAction.performAction(session);
                logger.debug("Completed non-transactional database session");
                return response;
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    /**
     * Saves an entity to the database.
     *
     * @param databaseEntity   The entity to persist in the database.
     * @param <DatabaseEntity> The type of the entity to persist.
     */
    public <DatabaseEntity> void saveEntity(DatabaseEntity databaseEntity) {
        performDatabaseAction(
            (Session session) -> {
                session.persist(databaseEntity);
                return Optional.empty();
            },
            true);
    }

    /**
     * Finds an entity in the database.
     *
     * @param classInstance    The entity's class instance (e.g. LoanApplicant.class).
     * @param id               The primary key to find the entity with.
     * @param <DatabaseEntity> The type of the entity to find in the database.
     * @param <ID>             The type of the entity's primary key.
     * @return The entity, or null if the entity was not found.
     */
    public <DatabaseEntity, ID> DatabaseEntity findEntityById(Class<DatabaseEntity> classInstance, ID id) {
        Optional<DatabaseEntity> response = performDatabaseAction((Session session) -> {
            DatabaseEntity foundDatabaseEntity = session.find(classInstance, id);
            return foundDatabaseEntity == null ? Optional.empty() : Optional.of(foundDatabaseEntity);
        }, true);

        return response.orElse(null);
    }

    /**
     * Finds all entities in the database.
     *
     * @param classInstance    The class instance of the entities to find (e.g. LoanApplicant.class).
     * @param <DatabaseEntity> The type of the entities to find in the database.
     * @return A list of all entities in the database.
     */
    public <DatabaseEntity> List<DatabaseEntity> findAllEntities(Class<DatabaseEntity> classInstance) {
        Optional<List<DatabaseEntity>> response = performDatabaseAction((Session session) -> {
            Query<DatabaseEntity> query = session.createQuery("FROM " + classInstance.getName(), classInstance);
            return Optional.of(query.getResultList());
        }, true);

        return response.orElse(null);
    }

    /**
     * Updates an entity in the database.
     *
     * @param databaseEntity   The entity to update in the database.
     * @param <DatabaseEntity> The type of the entity to update.
     */
    public <DatabaseEntity> void updateEntity(DatabaseEntity databaseEntity) {
        performDatabaseAction((Session session) -> {
            session.merge(databaseEntity);
            return Optional.empty();
        }, true);
    }

    /**
     * Deletes an entity from the database.
     *
     * @param databaseEntity   The entity to delete from the database.
     * @param <DatabaseEntity> The type of the entity to delete.
     */
    public <DatabaseEntity> void deleteEntity(DatabaseEntity databaseEntity) {
        performDatabaseAction((Session session) -> {
            session.remove(databaseEntity);
            return Optional.empty();
        }, true);
    }

    /**
     * Synchronise programmatically-defined enum values with entities in the database.
     *
     * @param acceptedDefs     The enum values to accept as valid database entities.
     * @param classInstance    The database entity class.
     * @param <DatabaseEntity> The database entity type.
     */
    public <DatabaseEntity> void synchronise(IDef<DatabaseEntity>[] acceptedDefs, Class<DatabaseEntity> classInstance) {
        Database.getInstance().performDatabaseAction((Session session) -> {
            List<DatabaseEntity> existingEntities =
                session.createQuery("FROM " + classInstance.getName(), classInstance).list();

            // Update or add accepted enum values to the database
            for (IDef<DatabaseEntity> acceptedDef : acceptedDefs) {
                DatabaseEntity databaseEntity = acceptedDef.toEntity();

                if (existingEntities.contains(databaseEntity)) {
                    DatabaseEntity existingDatabaseEntity = existingEntities.get(existingEntities.indexOf(databaseEntity));
                    acceptedDef.updateEntity(existingDatabaseEntity);
                    session.merge(existingDatabaseEntity);
                } else {
                    session.persist(databaseEntity);
                }
            }

            // Remove entities from the database that are not in the accepted enum values
            existingEntities.removeIf(existingDatabaseEntity -> Arrays.stream(acceptedDefs)
                .anyMatch(acceptedDef -> acceptedDef.toEntity().equals(existingDatabaseEntity)));
            for (DatabaseEntity databaseEntityToRemove : existingEntities) {
                session.remove(databaseEntityToRemove);
            }
            return Optional.empty();
        }, true);
    }
}
