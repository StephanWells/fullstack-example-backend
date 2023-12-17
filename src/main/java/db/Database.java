package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.NotNull;
import util.Props;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
     * Initialise the database. This method will import the properties, create the database, store the connection,
     * initialise the Hibernate ORM and store the session factory for the database.
     *
     * @throws ExceptionInInitializerError When the database or Hibernate fail to be initialised.
     */
    public static void initialise() {
        final String dbUrl = Props.getDbHost() + ":" + Props.getDbPort();
        final String dbUser = Props.getDbUser();
        final String dbPassword = Props.getDbPassword();
        final String dbName = Props.getDbName();
        Database db;

        // Initialise MySQL database
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS " + dbName;
            try (PreparedStatement statement = connection.prepareStatement(sqlCreateDB)) {
                statement.executeUpdate();
            }
            db = new Database(connection);
        } catch (SQLException e) {
            logger.error("Error initialising database");
            throw new ExceptionInInitializerError(e);
        }

        // Initialise Hibernate
        try {
            Configuration config = new Configuration();
            config.configure(HIBERNATE_CONFIG_FILE);
            config.setProperty("hibernate.connection.url",
                Props.getDbHost() + ":" + Props.getDbPort() + "/" + Props.getDbName());
            config.setProperty("hibernate.connection.username", Props.getDbUser());
            config.setProperty("hibernate.connection.password", Props.getDbPassword());
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).configure().build();
            db.sessionFactory = new Configuration().configure(HIBERNATE_CONFIG_FILE).buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            logger.error("Error initialising Hibernate ORM");
            throw new ExceptionInInitializerError(e);
        }
        logger.info("Database " + dbName + " initialised");
        Database.instance = db;
    }

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
     * Closes a given Hibernate session.
     *
     * @param session The session to close.
     * @throws IllegalStateException When the session is already closed.
     */
    public void closeSession(@NotNull Session session) {
        if (!session.isOpen()) {
            throw new IllegalStateException("Session is already closed");
        }
        session.close();
    }

    /**
     * A private interface to allow passing a lambda function to wrap inside a database transaction.
     *
     * @param <Response> The optional response expected from the lambda function.
     */
    @FunctionalInterface
    interface ITransactionAction<Response> {
        Optional<Response> performAction(Session session);
    }

    /**
     * A generic method to wrap an action inside a database transaction.
     *
     * @param transactionAction The action to wrap inside a database transaction.
     * @param <Response>        The response expected from the action.
     * @return An optional response (if the action in the transaction is supposed to pass a response).
     */
    public <Response> Optional<Response> performTransaction(ITransactionAction<Response> transactionAction) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            Optional<Response> response = transactionAction.performAction(session);
            transaction.commit();
            closeSession(session);
            return response;
        } catch (Exception e) {
            logger.error(e);
            throw (e);
        }
    }

    /**
     * Saves an entity to the database.
     *
     * @param entity   The entity to persist in the database.
     * @param <Entity> The type of the entity to persist.
     */
    public <Entity> void saveEntity(Entity entity) {
        performTransaction((Session session) -> {
            session.persist(entity);
            return Optional.empty();
        });
    }

    /**
     * Finds an entity in the database.
     *
     * @param classInstance The entity's class instance (e.g. LoanApplicant.class).
     * @param id            The primary key to find the entity with.
     * @param <Entity>      The type of the entity to find in the database.
     * @param <ID>          The type of the entity's primary key.
     * @return The entity, or null if the entity was not found.
     */
    public <Entity, ID> Entity findEntityById(Class<Entity> classInstance, ID id) {
        Optional<Entity> response = performTransaction((Session session) -> {
            Entity foundEntity = session.find(classInstance, id);
            return foundEntity == null ? Optional.empty() : Optional.of(foundEntity);
        });

        if (response.isEmpty()) {
            return null;
        }
        return response.get();
    }

    /**
     * Finds all entities in the database.
     *
     * @param classInstance The class instance of the entities to find (e.g. LoanApplicant.class).
     * @param <Entity>      The type of the entities to find in the database.
     * @return A list of all entities in the database.
     */
    public <Entity> List<Entity> findAllEntities(Class<Entity> classInstance) {
        Optional<List<Entity>> response = performTransaction((Session session) -> {
            String statement = "FROM " + classInstance.getName();
            Query<Entity> query = session.createQuery(statement, classInstance);
            return Optional.of(query.getResultList());
        });

        if (response.isEmpty()) {
            return null;
        }
        return response.get();
    }

    /**
     * Updates an entity in the database.
     *
     * @param entity   The entity to update in the database.
     * @param <Entity> The type of the entity to update.
     */
    public <Entity> void updateEntity(Entity entity) {
        performTransaction((Session session) -> {
            session.merge(entity);
            return Optional.empty();
        });
    }

    /**
     * Deletes an entity from the database.
     *
     * @param entity   The entity to delete from the database.
     * @param <Entity> The type of the entity to delete.
     */
    public <Entity> void deleteEntity(Entity entity) {
        performTransaction((Session session) -> {
            session.remove(entity);
            return Optional.empty();
        });
    }
}
