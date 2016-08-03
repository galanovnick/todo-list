package com.teamedv.javaclasses.todolist.entity;

import com.teamedv.javaclasses.todolist.entity.tiny.EntityId;

/**
 * Tiny type for entity.
 * @param <ObjectId> - entity id
 */
public interface Entity<ObjectId extends EntityId> {

    void setId(ObjectId id);

    ObjectId getId();
}