package com.teamedv.javaclasses.todolist.repository.impl;

import com.teamedv.javaclasses.todolist.entity.UserTask;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserTaskId;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UserTasksRepository extends InMemoryRepository<UserTask, UserTaskId> {

    private static UserTasksRepository instance;

    private final AtomicLong idCounter = new AtomicLong(0L);

    public Collection<UserTask> getUserTasksByUser(UserId userId) {
        return entries.values().stream()
                .filter(userTask -> userTask.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    protected UserTaskId nextId() {
        return new UserTaskId(idCounter.getAndAdd(1L));
    }

    public static UserTasksRepository getInstance() {
        if (instance == null) {
            return instance = new UserTasksRepository();
        }
        return instance;
    }

}
