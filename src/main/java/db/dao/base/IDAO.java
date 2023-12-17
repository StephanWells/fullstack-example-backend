package db.dao.base;

import java.util.List;

/**
 * An interface for implementing a DAO (Data Access Object) for database operations on a given entity.
 *
 * @param <Entity> The entity type to make a DAO for.
 * @param <ID>     The type of the primary key of the entity.
 */
public interface IDAO<Entity, ID> {
    default void validate(Entity entity) {
    }

    void save(Entity entity);

    Entity find(ID id);

    List<Entity> findAll();

    void update(Entity entity);

    void delete(ID id);
}
