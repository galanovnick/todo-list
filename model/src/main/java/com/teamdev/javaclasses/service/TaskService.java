package com.teamdev.javaclasses.service;

import com.teamdev.javaclasses.service.dto.TaskDto;
import com.teamedv.javaclasses.todolist.entity.Task;
import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

import java.util.Collection;

public interface TaskService {

    TaskId add(Task task);

    TaskDto getById(TaskId taskId);

    Collection<TaskDto> getByUser(UserId userId);

    void editTask(TaskId taskId, Task task);

    void delete(TaskId taskId);
}
