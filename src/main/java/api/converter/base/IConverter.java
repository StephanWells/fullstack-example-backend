package api.converter.base;

/**
 * An interface for defining a converter that can convert to and from database entities / DTOs (Data Transfer Objects).
 *
 * @param <Entity>    The database entity type.
 * @param <EntityDTO> The DTO type.
 */
public interface IConverter<Entity, EntityDTO> {
    EntityDTO toDTO(Entity e);

    Entity toModel(EntityDTO e);
}
