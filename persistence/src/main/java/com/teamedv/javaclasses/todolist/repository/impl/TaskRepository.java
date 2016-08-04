package com.teamedv.javaclasses.todolist.repository.impl;

import com.teamedv.javaclasses.todolist.entity.Task;
import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;

import java.util.concurrent.atomic.AtomicLong;

public class TaskRepository extends InMemoryRepository<Task, TaskId> {

    private static TaskRepository instance;

    private final AtomicLong idCounter = new AtomicLong(0L);

    private TaskRepository(){}

    @Override
    protected TaskId nextId() {
        return new TaskId(idCounter.getAndAdd(1L));
    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            return instance = new TaskRepository();
        }
        return instance;
    }

}
