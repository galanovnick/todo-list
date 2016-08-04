package com.teamdev.javaclasses.service.impl;

import com.teamdev.javaclasses.service.TaskService;
import com.teamdev.javaclasses.service.dto.TaskDto;
import com.teamedv.javaclasses.todolist.entity.Task;
import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

import java.util.Collection;

public class TaskServiceImpl implements TaskService {

    private static TaskService instance;

    @Override
    public TaskId add(Task task) {
        return null;
    }

    @Override
    public TaskDto getById(TaskId taskId) {
        return null;
    }

    @Override
    public Collection<TaskDto> getByUser(UserId userId) {
        return null;
    }

    @Override
    public void editTask(TaskId taskId, Task task) {

    }

    @Override
    public void delete(TaskId taskId) {

    }

    private TaskServiceImpl(){}

    public static TaskService getInstance() {
        if (instance == null) {
            return instance = new TaskServiceImpl();
        }
        return instance;
    }
}
