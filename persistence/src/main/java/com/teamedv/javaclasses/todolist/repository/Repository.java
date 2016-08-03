package com.teamedv.javaclasses.todolist.repository;

import com.teamedv.javaclasses.todolist.entity.Entity;
import com.teamedv.javaclasses.todolist.entity.tiny.EntityId;

import java.util.Collection;
import java.util.Optional;

/**
 * The repository maintains CRUD operations.
 * @param <ObjectType> - object type
 * @param <ObjectId> - object id
 */
interface Repository<ObjectType extends Entity<ObjectId>, ObjectId extends EntityId> {

    /**
     * Add object to repository.
     * @param object - object to be added
     * @return - added object id
     */
    ObjectId add(ObjectType object);

    /**
     * Find object by id.
     * @param id - object id.
     * @return - optional: null or found object
     */
    Optional<ObjectType> findOne(ObjectId id);

    /**
     * Find all objects.
     * @return - object collection.
     */
    Collection<ObjectType> findAll();

    /**
     * Delete specific object by id.
     * @param id - object id
     */
    void delete(ObjectId id);
}
