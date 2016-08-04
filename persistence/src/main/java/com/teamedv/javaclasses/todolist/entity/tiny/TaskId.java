package com.teamedv.javaclasses.todolist.entity.tiny;

public class TaskId implements EntityId<Long> {

    private final Long id;

    public TaskId(Long id) {
        this.id = id;
    }

    public Long value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskId taskId = (TaskId) o;

        return id != null ? id.equals(taskId.id) : taskId.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
