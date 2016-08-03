package com.teamedv.javaclasses.todolist.entity.tiny;

/**
 * Tiny type for entity id.
 * @param <IdType> - real type of entity id.
 */
public interface EntityId<IdType> {

    /**
     * Return real id value.
     * @return - id value.
     */
    IdType value();
}
