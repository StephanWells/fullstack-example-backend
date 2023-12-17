package db.dao.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * An interface for implementing a DAO (Data Access Object) for database operations on a given entity.
 *
 * @param <Entity> The entity type to make a DAO for.
 * @param <ID>     The type of the primary key of the entity.
 */
public interface IDAO<Entity, ID> {
    static final Logger logger = LogManager.getLogger(IDAO.class);

    default void validate(Entity entity) {
        logger.warn("Validate method unimplemented");
    }

    default void save(Entity entity) {
        logger.warn("Save method unimplemented");
    }

    default Entity find(ID id) {
        logger.warn("Find method unimplemented");
        return null;
    }

    default List<Entity> findAll() {
        logger.warn("Find all method unimplemented");
        return null;
    }

    default void update(Entity entity) {
        logger.warn("Update method unimplemented");
    }

    default void delete(ID id) {
        logger.warn("Delete method unimplemented");
    }
}
