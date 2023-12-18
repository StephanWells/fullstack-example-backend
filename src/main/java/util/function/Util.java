package util.function;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class with static methods for general utility functionality.
 */
public class Util {
    /**
     * Gets a list of classes from a given package.
     *
     * @param packageName The package to return the classes from, e.g. "db.model".
     * @return A list of classes within that package.
     * @throws ClassNotFoundException When the class derived from the class file does not exist.
     * @throws URISyntaxException     When the resource URI retrieved from the class loader is an invalid URI.
     */
    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException,
        URISyntaxException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(Objects.requireNonNull(
            Thread.currentThread().getContextClassLoader().getResource(packageName.replace('.', '/'))).toURI()
        );

        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() -
                            ".class".length());
                        classes.add(Class.forName(className));
                    }
                }
            }
        }

        return classes;
    }
}
