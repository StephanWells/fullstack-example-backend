package util.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * A singleton class to provide properties from the application.properties file.
 */
public class Props {
    private static Properties props;

    private static final String PROPS_FILE = "application.properties";
    private static final Logger logger = LogManager.getLogger(Props.class);

    /**
     * initialises the props instance. This method will load the application.properties file and store it internally.
     */
    public static void initialise() {
        props = new Properties();
        try (InputStream input = db.Database.class.getClassLoader().getResourceAsStream(PROPS_FILE)) {
            props.load(input);
            logger.info("Props initialised");
        } catch (Exception e) {
            logger.error("Error loading application.properties file");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getDbHost() {
        return props.getProperty("db.host");
    }

    public static String getDbPort() {
        return props.getProperty("db.port");
    }

    public static String getDbUser() {
        return props.getProperty("db.username");
    }

    public static String getDbPassword() {
        return props.getProperty("db.password");
    }

    public static String getDbName() {
        return props.getProperty("db.name");
    }

    public static int getApiPort() {
        return Integer.parseInt(props.getProperty("api.port"));
    }

    public static String getApiHost() {
        return props.getProperty("api.host");
    }
}

