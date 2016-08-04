package com.teamedv.javaclasses.todolist.entity;

import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

public class Task implements Entity<TaskId> {

    private TaskId id = new TaskId(0L);
    private String description;
    private UserId creatorId;
    private boolean done = false;

    public Task(String description, UserId creatorId) {
        this.description = description;
        this.creatorId = creatorId;
    }

    @Override
    public TaskId getId() {
        return id;
    }

    @Override
    public void setId(TaskId id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserId getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UserId creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (done != task.done) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        return creatorId != null ? creatorId.equals(task.creatorId) : task.creatorId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
    }
}
