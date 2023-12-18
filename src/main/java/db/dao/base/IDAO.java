package db.dao.base;

import defs.errors.NotImplementedException;
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
        throw new NotImplementedException("Validate method unimplemented");
    }

    default void save(Entity entity) {
        throw new NotImplementedException("Save method unimplemented");
    }

    default Entity find(ID id) {
        throw new NotImplementedException("Find method unimplemented");
    }

    default List<Entity> findAll() {
        throw new NotImplementedException("Find all method unimplemented");
    }

    default void update(Entity entity) {
        throw new NotImplementedException("Update method unimplemented");
    }

    default void delete(ID id) {
        throw new NotImplementedException("Delete method unimplemented");
    }
}
