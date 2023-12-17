package defs.base;

/**
 * An interface for implementing enum definitions, e.g. currencies or loan types.
 *
 * @param <Entity> The entity to convert the definition to when storing to the database.
 */
public interface IDef<Entity> {
    /**
     * Convert an enum definition to a database entity (used for entity creation).
     *
     * @return A database entity from the enum definition.
     */
    Entity toEntity();

    /**
     * Update a database entity with this enum definition's fields.
     *
     * @param entity The entity to update.
     */
    void updateEntity(Entity entity);
}
