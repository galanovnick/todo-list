package com.teamedv.javaclasses.todolist.entity.tiny;

public class UserTaskId implements EntityId<Long> {

    private final Long id;

    public UserTaskId(Long id) {
        this.id = id;
    }

    public Long value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTaskId that = (UserTaskId) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
