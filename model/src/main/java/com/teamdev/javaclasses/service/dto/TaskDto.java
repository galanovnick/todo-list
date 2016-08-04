package com.teamdev.javaclasses.service.dto;

import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

import java.util.Date;

public class TaskDto {

    private TaskId taskId = new TaskId(0L);
    private String description;
    private UserId creatorId;
    private String creationDate;
    private boolean done;

    public TaskDto(String description, UserId creatorId, String creationDate, boolean done) {
        this.description = description;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
        this.done = done;
    }

    public TaskDto(TaskId taskId, String description, UserId creatorId, String creationDate, boolean done) {
        this.taskId = taskId;
        this.description = description;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
        this.done = done;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public TaskId getTaskId() {
        return taskId;
    }

    public void setTaskId(TaskId taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDto taskDto = (TaskDto) o;

        if (done != taskDto.done) return false;
        if (taskId != null ? !taskId.equals(taskDto.taskId) : taskDto.taskId != null) return false;
        if (description != null ? !description.equals(taskDto.description) : taskDto.description != null) return false;
        if (creatorId != null ? !creatorId.equals(taskDto.creatorId) : taskDto.creatorId != null) return false;
        return creationDate != null ? creationDate.equals(taskDto.creationDate) : taskDto.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
    }
}
