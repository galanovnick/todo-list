package com.teamedv.javaclasses.todolist.entity;

import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserTaskId;

public class UserTask implements Entity<UserTaskId> {

    private UserTaskId id = new UserTaskId(0L);
    private UserId userId;
    private TaskId taskId;

    public UserTask(UserId userId, TaskId taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public UserTaskId getId() {
        return id;
    }

    public void setId(UserTaskId id) {
        this.id = id;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
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

        UserTask userTask = (UserTask) o;

        if (id != null ? !id.equals(userTask.id) : userTask.id != null) return false;
        if (userId != null ? !userId.equals(userTask.userId) : userTask.userId != null) return false;
        return taskId != null ? taskId.equals(userTask.taskId) : userTask.taskId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        return result;
    }
}
